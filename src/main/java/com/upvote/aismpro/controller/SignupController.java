package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.LoginUserDTO;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.security.SecurityService;
import com.upvote.aismpro.service.SignupServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
public class SignupController {
    @Autowired
    private SignupServiceInter signupService;

    @Autowired
    private SecurityService securityService;

    // 이메일 중복 확인
    @GetMapping("/signup/email/validate/{email}")
    public @ResponseBody
    Map<String, Boolean> emailDoubleCheck(@PathVariable("email") String email) {
        System.out.println("== email Double Check : " + email);
        try {
            signupService.emailDoubleCheck(email);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return Collections.singletonMap("result", false);
        }
        return Collections.singletonMap("result", true);
    }

    // 닉네임 중복 확인
    @GetMapping("/nickname/validate/{nickName}")
    public ResponseEntity<Boolean> nickDoubleCheck(@PathVariable("nickName") String nickName) {
        System.out.println("== nickName Double Check : " + nickName);
        try {
            signupService.nickDoubleCheck(nickName);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/signup/{nickName}")
    public ResponseEntity<LoginUserDTO> signup(HttpServletRequest request, @PathVariable("nickName") String nickName) {
        try {
            HttpSession session = request.getSession();
            String platform = session.getAttribute("platform").toString();

            User user = new User(
                    nickName,
                    session.getAttribute("email").toString(),
                    platform
            );

            signupService.signup(user);

             //user token 생성
            String token = securityService.createToken(securityService.transformUserToJwtRequestDto(user));
            // 로그인 정보 전달 DTO 생성
            LoginUserDTO loginUser = new LoginUserDTO(token, user, platform);

            return new ResponseEntity<>(loginUser, HttpStatus.OK);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
