package com.upvote.aismpro.controller;


import com.upvote.aismpro.entity.BookEntity;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.loginverifier.GoogleTokenVerifier;
import com.upvote.aismpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
public class LoginController {

    @Autowired
    GoogleTokenVerifier googleVerifier;

    @Autowired
    private UserRepository userRepo;

    // 구글 토큰 유효성 검증
    @PostMapping("/tokenVerify")
    public void tokenVerify(@RequestBody Map param){
        System.out.println("RequestBody value : " + param.get("tokenId"));
        googleVerifier.tokenVerify((String) param.get("tokenId"));
    }

//    @GetMapping ("/nickNameDoubleCheck")
//    public @ResponseBody Map<String, Boolean> nickDoubleCheck(@RequestParam("nickName") String nickName) {
//
//    }

    @PostMapping("/signup.do")
    public @ResponseBody Map<String, Boolean> signup(@RequestBody Map<String, String> info) {
        System.out.println(info.get("nickName") + " " + info.get("email"));

        return Collections.singletonMap("result", true);
    }

}
