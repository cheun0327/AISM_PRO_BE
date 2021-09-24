package com.upvote.aismpro.controller;


import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.service.SignupServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
public class SignupController {
    @Autowired
    private SignupServiceInter signup;

    // oAuth 로그인 정보 연동 안된 사용자가 연동하기 선택했을때
    @GetMapping("/linkage")
    public void linking() {

    }

    // 이메일 중복 확인
    @GetMapping("/isValidEmail/{email}")
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
    @GetMapping("/isValidNickName/{nickName}")
    public @ResponseBody
    Map<String, Boolean> nickDoubleCheck(@PathVariable("nickName") String nickName) {
        System.out.println("== nickName Double Check : " + nickName);
        try {
            signup.nickDoubleCheck(nickName);
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
            signup.signup(user);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonMap("result", false);
        }
        return Collections.singletonMap("result", true);
    }
}
