package com.upvote.aismpro.controller;

import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.security.SecurityService;
import com.upvote.aismpro.service.SignupServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
    public @ResponseBody Map<String, Boolean> signup(HttpServletRequest request, HttpSession tmpSession, @RequestBody User user) {
        try {
            Map<String, Object> map = new HashMap<>();
            // 일반 회원가입
            signup.signup(user);

            // 시도했던 소셜 로그인 정보 저장
            String snsTmpPlatform = tmpSession.getAttribute("platform").toString();
            String snsTmpEmail = tmpSession.getAttribute("snsEmail").toString();
            System.out.println(tmpSession.getAttribute("platform").toString() + tmpSession.getAttribute("snsEmail").toString());

            // 회원가입 계정과 oAuth 연동
            signup.linking(user.getId(), snsTmpPlatform, snsTmpEmail);

            //session 삭제
            tmpSession.invalidate();

            //userId로 token 생성
            String token = securityService.createToken(securityService.transformUserToJwtRequestDto(user));

            Map<String, String> data = new HashMap<String, String>() {{
                put("token", token);
                put("userId", user.getId());
                put("userEmail", user.getEmail());
                put("userNickName", user.getNickName());
            }};
            map.put("result", true);
            map.put("data", data);
            //TODO
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonMap("result", false);
        }
        return Collections.singletonMap("result", true);
    }
}
