package com.upvote.aismpro.controller;


import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.loginverifier.GoogleTokenVerifier;
import com.upvote.aismpro.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/signup.do")
    public @ResponseBody Map<String, Boolean> signup(@RequestBody User user) {
        login.signup(user);
        return Collections.singletonMap("result", true);
    }

}
