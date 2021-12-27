package com.upvote.aismpro.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upvote.aismpro.dto.LoginUserDTO;
import com.upvote.aismpro.dto.SignupDTO;
import com.upvote.aismpro.dto.SongSaveDTO;
import com.upvote.aismpro.service.SignupService;
import com.upvote.aismpro.vo.SignupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

@RestController
public class SignupController {

    @Autowired
    private SignupService accountService;

    @PostMapping("/signup")
    public ResponseEntity<LoginUserDTO> signup(@ModelAttribute SignupVO signupVO) {

        try {
            signupVO.print();
            SignupDTO signupDTO = new SignupDTO();
            switch (signupVO.getSns()) {
                case "카카오": {
//                    LinkedHashMap<String, Object> kakaoAccount = (LinkedHashMap<String, Object>) signupVO.getInfo().get("kakao_account");
//                    LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) signupVO.getInfo().get("properties");
//
//                    signupDTO = SignupDTO.builder()
//                            .sns(signupVO.getSns())
//                            .name((String) properties.get("nickname"))
//                            .email((String) kakaoAccount.get("email"))
//                            .nickname("nick")
//                            .build();
                    break;
                }
                case "구글": {
//                    signupDTO = SignupDTO.builder()
//                            .sns(signupVO.getSns())
//                            .name((String) signupVO.getInfo().get("name"))
//                            .email((String) signupVO.getInfo().get("email"))
//                            .nickname("nick")
//                            .build();
                    break;
                }
                case "네이버": {
                    // TODO 네이버 개발자 모드임.
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }

//            LoginUserDTO loginUserDTO = accountService.signup(signupDTO, signupVO.getProfileImg());
//            return new ResponseEntity<>(loginUserDTO, HttpStatus.OK);
            return new ResponseEntity<>(new LoginUserDTO(), HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
