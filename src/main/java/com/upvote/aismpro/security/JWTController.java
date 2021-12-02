package com.upvote.aismpro.security;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.UserDTO;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
public class JWTController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private CustomModelMapper modelMapper;

    @PostMapping("/token")
    public Map<String, Object> createToken(@RequestBody JWTRequestDTO jwtRequest) {
        String token = jwtService.createToken(jwtRequest);
        return Collections.singletonMap("token", token);
    }

    @GetMapping("/token/validate/{token}")
    public Map<String, Object> validateToken(@PathVariable("token") String token) {
        System.out.println(token);
        try {
//            Boolean isValid = jwtService.validateToken(token);
        } catch (ExpiredJwtException jwtE) {
            jwtE.printStackTrace();
            return Collections.singletonMap("isValid", false);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonMap("isValid", false);
        }
//        return Collections.singletonMap("isValid", jwtService.validateToken(token));
        return Collections.singletonMap("isValid", true);
    }

    @GetMapping("/token/validate/subject/{token}")
    public Map<String, Object> validateTokenWithSubject(@PathVariable("token") String token,
                                                        @RequestParam("subject") String subject) {
        System.out.println(token);
        System.out.println(subject);
        try {
            Boolean isValid = jwtService.validateTokenWithSubject(token, subject);
        } catch (ExpiredJwtException jwtE) {
            return Collections.singletonMap("isValid", false);
        }
        return Collections.singletonMap("isValid", jwtService.validateTokenWithSubject(token, subject));
    }

    // 로그인 유지
    // 토큰으로 사용자 정보 가져오기
    @GetMapping("/token/user/{token}")
    public ResponseEntity<UserDTO> getUserByToken(@PathVariable("token") String token) {

        User user = userRepository.getById(SecurityUtil.getCurrentUserId());

        return new ResponseEntity<>(modelMapper.toUserDTO().map(user, UserDTO.class), HttpStatus.OK);
    }

    // 프론트에서 토큰 인식 요청
    @GetMapping("/token/validate")
    public ResponseEntity<Boolean> validToken() {
//        User user = userRepository.getById(SecurityUtil.getCurrentUserId());
        try {
            System.out.println(SecurityUtil.getCurrentUserId());
            return new ResponseEntity<>(true, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/token/subject/{token}")
    // header에서 token 읽어오게 변경
    public Map<String, Object> getSubject(@PathVariable("token") String token) {
        String subject = jwtService.getSubject(token);
        return Collections.singletonMap("subject", subject);
    }
}
