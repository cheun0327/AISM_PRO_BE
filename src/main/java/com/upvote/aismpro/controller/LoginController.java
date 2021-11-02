package com.upvote.aismpro.controller;


import com.google.gson.JsonObject;
import com.upvote.aismpro.dto.LoginUserDTO;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.loginverifier.GoogleTokenVerifier;
import com.upvote.aismpro.loginverifier.NaverTokenVerifier;
import com.upvote.aismpro.security.SecurityService;
import com.upvote.aismpro.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@RestController
public class LoginController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    GoogleTokenVerifier googleVerifier;

    @Autowired
    NaverTokenVerifier naverTokenVerifier;

    @Autowired
    private LoginService login;

    // 카카오 로그인 정보 받음
    @PostMapping("/login/kakao")
    public ResponseEntity<LoginUserDTO> kakaoLogin(HttpServletRequest request, @RequestBody LinkedHashMap<String, Object> kakaoInfo){
        // 카카오 로그인 정보 json
        LinkedHashMap<String, Object> kakaoProfile = (LinkedHashMap<String, Object>) kakaoInfo.get("profile");
        // 카카오 로그인 유저 정보 json
        LinkedHashMap<String, Object> kakaoProfileInfo = (LinkedHashMap<String, Object>) kakaoProfile.get("kakao_account");

        LinkedHashMap<String, Object> profile = (LinkedHashMap<String, Object>) kakaoProfileInfo.get("profile");

        System.out.println("kakao 로그인 : " + kakaoProfileInfo.get("email"));
        try {
            System.out.println((String) profile.get("nickname") + kakaoProfileInfo.get("email"));
            User user = login.checkUser("kakao", (String) profile.get("nickname"), (String) kakaoProfileInfo.get("email"));
            // token 생성
            String token = securityService.createToken(securityService.transformUserToJwtRequestDto(user));

            return new ResponseEntity<>(new LoginUserDTO(token, user), HttpStatus.OK);

        } catch (NoSuchElementException e){
            e.printStackTrace();

            // Session 설정
            HttpSession session = request.getSession();

            session.setAttribute("platform", "카카오");
            session.setAttribute("name", (String) profile.get("nickname"));
            session.setAttribute("email", kakaoProfileInfo.get("email"));
            session.setMaxInactiveInterval(6*60*60);

            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login/google")
    public ResponseEntity<LoginUserDTO> googleLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody LinkedHashMap<String, Object> googleInfo) {
        // 구글 로그인 정보 json
        LinkedHashMap<String, String> googleProfile = (LinkedHashMap<String, String>) googleInfo.get("profile");

        System.out.println("google 로그인 : " + googleProfile.get("email"));

        try {
            User user = login.checkUser("google", googleProfile.get("name"), googleProfile.get("email"));

            // token 생성
            String token = securityService.createToken(securityService.transformUserToJwtRequestDto(user));

            return new ResponseEntity<>(new LoginUserDTO(token, user), HttpStatus.OK);

        } catch (NoSuchElementException e) {
            e.printStackTrace();

            HttpSession session = request.getSession();
            System.out.println("생성 세션 : " + session.getId());

            session.setAttribute("platform", "구글");
            session.setAttribute("name", googleProfile.get("name"));
            session.setAttribute("email", googleProfile.get("email"));
            session.setMaxInactiveInterval(6*60*60);

            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/login/naver")
    public @ResponseBody Map<String, Object> naverLogin(HttpServletRequest request, @RequestParam("access_token") String access_token) throws IOException {
        Map<String, Object> naverProfile = naverTokenVerifier.getUserInfo(access_token);

        try {
            String userId = login.snsLinkageCheck("naver", (String) ((String) naverProfile.get("email")).replace("\"", ""));
            Map<String, Object> map = new HashMap<>();

            // sns 로그인 정보로 og 회원 정보 가져오기
            User user = login.getUserInfo(userId);

            //userId로 token 생성
            String token = securityService.createToken(securityService.transformUserToJwtRequestDto(user));

            Map<String, String> data = new HashMap<String, String>() {{
                put("token", token);
                put("userId", userId);
                put("userEmail", user.getEmail());
                put("userNickName", user.getNickName());
            }};
            map.put("result", true);
            map.put("data", data);

            return map;
        } catch (EntityNotFoundException e){
            e.printStackTrace();
            // Session 설정
            HttpSession tmpSession = request.getSession();

            tmpSession.setAttribute("platform", "네이버");
            tmpSession.setAttribute("snsEmail", naverProfile.get("email"));

            System.out.println(tmpSession.getAttribute("platform").toString() + tmpSession.getAttribute("snsEmail").toString());

            return Collections.singletonMap("result", false);
        }
    }

    // 로그인 성공 이후 사용자 정보 전달
    @GetMapping("/getUserInfo")
    public User getUserInfo(@RequestParam("userID") String userID) {
        return login.getUserInfo(userID);
    }
}
