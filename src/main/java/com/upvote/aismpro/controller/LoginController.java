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

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class LoginController {

    @Autowired
    GoogleTokenVerifier googleVerifier;

    @Autowired
    private LoginService login;

    // 카카오 로그인 정보 받음
    @PostMapping("/login/kakao")
    public @ResponseBody Map<String, Object> kakaoLogin(@RequestBody LinkedHashMap<String, Object> kakaoInfo) {
        // 카카오 로그인 정보 json
        LinkedHashMap<String, Object> kakaoProfile = (LinkedHashMap<String, Object>) kakaoInfo.get("profile");
        // 카카오 로그인 유저 정보 json
        LinkedHashMap<String, String> kakaoProfileInfo = (LinkedHashMap<String, String>) kakaoProfile.get("kakao_account");

        // Oauth info에서 이메일로 정보 찾고 없으면 있으면 로그인 시키고 아니면 없다고 알려줌(회원가입하거나, 연동해야함)
        try {
            String userId = login.snsLinkageCheck("kakao", kakaoProfileInfo.get("email"));
            Map<String, Object> map = new HashMap<>();
            map.put("result", true);
            map.put("userId", userId);
            return map;
        } catch (EntityNotFoundException e){
            e.printStackTrace();
            return Collections.singletonMap("result", false);
        }
    }

    @PostMapping("/login/google")
    public @ResponseBody Map<String, Object> googleLogin(@RequestBody LinkedHashMap<String, Object> googleInfo) {
        // 구글 로그인 정보 json
        LinkedHashMap<String, String> googleProfile = (LinkedHashMap<String, String>) googleInfo.get("profile");

        try {
            String userId = login.snsLinkageCheck("google", googleProfile.get("email"));
            Map<String, Object> map = new HashMap<>();
            map.put("result", true);
            map.put("userId", userId);
            return map;
        } catch (EntityNotFoundException e){
            e.printStackTrace();
            return Collections.singletonMap("result", false);
        }
    }

    @PostMapping("/login/naver")
    public @ResponseBody Map<String, Object> naverLogin(@RequestBody LinkedHashMap<String, Object> naverLogin) {

        // TODO
        // Naver 회원 정보 가공

        try {
            String userId = login.snsLinkageCheck("naver", "여기에 이메일 넣어주세요.");
            Map<String, Object> map = new HashMap<>();
            map.put("result", true);
            map.put("userId", userId);
            return map;
        } catch (EntityNotFoundException e){
            e.printStackTrace();
            return Collections.singletonMap("result", false);
        }
    }

    // 로그인 성공 이후 사용자 정보 전달
    @GetMapping("/getUserInfo")
    public Optional<User> getUserInfo(@RequestParam("userID") String userID) {
        return login.getUserInfo(userID);
    }

}
