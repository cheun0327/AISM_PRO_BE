package com.upvote.aismpro.controller;


import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.loginverifier.GoogleTokenVerifier;
import com.upvote.aismpro.loginverifier.NaverTokenVerifier;
import com.upvote.aismpro.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    GoogleTokenVerifier googleVerifier;

    @Autowired
    NaverTokenVerifier naverTokenVerifier;

    @Autowired
    private LoginService login;

    // 카카오 로그인 정보 받음
    @PostMapping("/login/kakao")
    public @ResponseBody Map<String, Object> kakaoLogin(HttpServletRequest request, @RequestBody LinkedHashMap<String, Object> kakaoInfo){
        // 카카오 로그인 정보 json
        LinkedHashMap<String, Object> kakaoProfile = (LinkedHashMap<String, Object>) kakaoInfo.get("profile");
        // 카카오 로그인 유저 정보 json
        LinkedHashMap<String, String> kakaoProfileInfo = (LinkedHashMap<String, String>) kakaoProfile.get("kakao_account");

        System.out.println("kakao 로그인 : " + kakaoProfileInfo.get("email"));
        // Oauth info에서 이메일로 정보 찾고 없으면 있으면 로그인 시키고 아니면 없다고 알려줌(회원가입하거나, 연동해야함)
        try {
            String userId = login.snsLinkageCheck("kakao", kakaoProfileInfo.get("email"));
            Map<String, Object> map = new HashMap<>();

            // sns 로그인 정보로 og 회원 정보 가져오기
            User user = login.getUserInfo(userId);

            // Session 설정
            HttpSession session = request.getSession();

            session.setAttribute("userId", user.getId());
            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("userNickName", user.getNickName());

            map.put("result", true);
            map.put("userId", userId);
            return map;
        } catch (EntityNotFoundException e){
            e.printStackTrace();
            return Collections.singletonMap("result", false);
        }
    }

    @PostMapping("/login/google")
    public @ResponseBody Map<String, Object> googleLogin(HttpServletRequest request, @RequestBody LinkedHashMap<String, Object> googleInfo) {
        // 구글 로그인 정보 json
        LinkedHashMap<String, String> googleProfile = (LinkedHashMap<String, String>) googleInfo.get("profile");

        try {
            String userId = login.snsLinkageCheck("google", googleProfile.get("email"));
            Map<String, Object> map = new HashMap<>();

            // sns 로그인 정보로 og 회원 정보 가져오기
            User user = login.getUserInfo(userId);

            // Session 설정
            HttpSession session = request.getSession();

            session.setAttribute("userId", user.getId());
            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("userNickName", user.getNickName());

            map.put("result", true);
            map.put("userId", userId);
            return map;
        } catch (EntityNotFoundException e){
            e.printStackTrace();
            return Collections.singletonMap("result", false);
        }
    }

    @GetMapping("/login/naver")
    public @ResponseBody Map<String, Object> naverLogin(HttpServletRequest request, @RequestParam("access_token") String access_token) throws IOException {
        Map<String, Object> naverProfile = naverTokenVerifier.getUserInfo(access_token);

        try {
            String userId = login.snsLinkageCheck("Naver", (String) ((String) naverProfile.get("email")).replace("\"", ""));
            Map<String, Object> map = new HashMap<>();

            // sns 로그인 정보로 og 회원 정보 가져오기
            User user = login.getUserInfo(userId);

            // Session 설정
            HttpSession session = request.getSession();

            session.setAttribute("userId", user.getId());
            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("userNickName", user.getNickName());

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
    public User getUserInfo(@RequestParam("userID") String userID) {
        return login.getUserInfo(userID);
    }
}
