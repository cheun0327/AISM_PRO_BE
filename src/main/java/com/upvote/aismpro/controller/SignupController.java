package com.upvote.aismpro.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upvote.aismpro.dto.LoginUserDTO;
import com.upvote.aismpro.dto.SignupDTO;
import com.upvote.aismpro.dto.SongSaveDTO;
import com.upvote.aismpro.service.SignupService;
import com.upvote.aismpro.vo.SignupVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestController
public class SignupController {

    @Autowired
    private SignupService accountService;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/signup")
    public ResponseEntity<LoginUserDTO> signup(@RequestParam String sns,
                                               @RequestParam String nickname,
                                               @RequestParam String info,
                                               @RequestParam @Nullable MultipartFile profileImg) {

        log.info("sns={}", sns);
        log.info("nickname={}", nickname);
        log.info("info={}", info);

        Map<String, String> infoMap = null;
        try {
            infoMap = objectMapper.readValue(info, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // SignupDTO 만들기
        SignupDTO signupDTO = SignupDTO.builder()
                        .sns(sns)
                        .name(infoMap.get("name"))
                        .email(infoMap.get("email"))
                        .nickname(nickname)
                        .build();
        System.out.println(signupDTO);

        LoginUserDTO loginUserDTO = null;
        try {
            // 프로필 이미지 (O)
            if (profileImg != null) {
                log.info("img={}", profileImg);
                log.info("imgFileName={}", profileImg.getOriginalFilename());
                loginUserDTO = accountService.signup(signupDTO, profileImg);
            }

            // 프로필 이미지 (X)
            else {
                log.info("profileImg is NULL");
                loginUserDTO = accountService.signupWithoutProfile(signupDTO);
            }
            return new ResponseEntity<>(loginUserDTO, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signup/no-profile")
    public ResponseEntity<LoginUserDTO> signupWithoutProfile(@RequestBody SignupVO signupVO) {
        try {
            SignupDTO signupDTO = new SignupDTO();
            switch (signupVO.getSns()) {
                case "카카오": {
                    LinkedHashMap<String, Object> kakaoAccount = (LinkedHashMap<String, Object>) signupVO.getInfo().get("kakao_account");
                    LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) signupVO.getInfo().get("properties");

                    signupDTO = SignupDTO.builder()
                            .sns(signupVO.getSns())
                            .name((String) properties.get("nickname"))
                            .email((String) kakaoAccount.get("email"))
                            .nickname(signupVO.getNickname())
                            .build();
                    break;
                }
                case "구글": {
                    signupDTO = SignupDTO.builder()
                            .sns(signupVO.getSns())
                            .name((String) signupVO.getInfo().get("name"))
                            .email((String) signupVO.getInfo().get("email"))
                            .nickname(signupVO.getNickname())
                            .build();
                    break;
                }
                case "네이버": {
                    // TODO 네이버 개발자 모드임.
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }

            LoginUserDTO loginUserDTO = accountService.signupWithoutProfile(signupDTO);
            return new ResponseEntity<>(loginUserDTO, HttpStatus.OK);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
