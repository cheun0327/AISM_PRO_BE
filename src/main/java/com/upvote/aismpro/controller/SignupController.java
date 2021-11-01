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
    private SignupServiceInter signup;

    @Autowired
    private SecurityService securityService;

    // oAuth 로그인 정보 연동 안된 사용자가 연동하기 페이지에서 연동 on을 클릭했을때
    // sns로그인 팝업창 뜨고, 인증되면 백에서 알아서 oAuth DB에 넣어주고
    // 리액트는 true 받으면 on/off 토글 변화
    // {platform : "", email: ""}
    @GetMapping("/snsLinking")
    public void linking(HttpSession session, @RequestParam("platform") String platform, @RequestParam("email") String email) {

    }

    // 이메일 중복 확인
    @GetMapping("/signup/email/validate/{email}")
    public @ResponseBody
    Map<String, Boolean> emailDoubleCheck(@PathVariable("email") String email) {
        System.out.println("== email Double Check : " + email);
        try {
            signup.emailDoubleCheck(email);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return Collections.singletonMap("result", false);
        }
        return Collections.singletonMap("result", true);
    }

    // 닉네임 중복 확인
    @GetMapping("/signup/nickname/validate/{nickName}")
    public ResponseEntity<Boolean> nickDoubleCheck(@PathVariable("nickName") String nickName) {
        System.out.println("== nickName Double Check : " + nickName);
        try {
            signup.nickDoubleCheck(nickName);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<LoginUserDTO> signup(HttpServletRequest request) throws Exception {
        try {
            HttpSession session = request.getSession();

            System.out.println("읽은 세션 : " + session.getId());

            System.out.println((String) session.getAttribute("email"));

            User user = new User(
                    session.getAttribute("name").toString(),
                    session.getAttribute("email").toString(),
                    session.getAttribute("platform").toString()
            );
            // user 등록
            signup.signup(user);
            // user token 생성
            String token = securityService.createToken(securityService.transformUserToJwtRequestDto(user));
            // 로그인 정보 전달 DTO 생성
            LoginUserDTO loginUser = new LoginUserDTO(token, user);

            return new ResponseEntity<>(loginUser, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException();
        }
    }
}
