package com.upvote.aismpro.controller;


import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.loginverifier.GoogleTokenVerifier;
import com.upvote.aismpro.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    GoogleTokenVerifier googleVerifier;

    @Autowired
    private LoginService login;

    // 구글 토큰 유효성 검증
    @PostMapping("/tokenVerify")
    public void tokenVerify(@RequestBody Map param){
        System.out.println("RequestBody value : " + param.get("tokenId"));
        googleVerifier.tokenVerify((String) param.get("tokenId"));
    }

    // 카카오 로그인 정보 받음
    @PostMapping("/login/kakao")
    public void kakaoLogin(@RequestBody Map<Object, Object> kakaoInfo) {
        System.out.println(kakaoInfo);
        // Oauth info에서 이메일로 정보 찾고 없으면 있으면 로그인 시키고 아니면 없다고 알려줌(회원가입하거나, 연동해야함)

    }

    // 닉네임 중복 확인
    @GetMapping ("/isValidNickName/{nickName}")
    public @ResponseBody Map<String, Boolean> nickDoubleCheck(@PathVariable("nickName") String nickName) {
        System.out.println("== nickName Double Check : " + nickName);
        try {
            login.nickDoubleCheck(nickName);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return Collections.singletonMap("result", false);
        }
        return Collections.singletonMap("result", true);
    }

    // 회원가입 실행
    @PostMapping("/signup.do")
    public @ResponseBody Map<String, Boolean> signup(@RequestBody User user) {
        try {
            login.signup(user);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonMap("result", false);
        }
        return Collections.singletonMap("result", true);
    }

}
