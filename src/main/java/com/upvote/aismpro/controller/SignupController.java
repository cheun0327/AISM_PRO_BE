package com.upvote.aismpro.controller;


import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.service.SignupServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;

@RestController
public class SignupController {
    @Autowired
    private SignupServiceInter signup;

    // oAuth 로그인 정보 연동 안된 사용자가 연동하기 페이지에서 연동 on을 클릭했을때
    // sns로그인 팝업창 뜨고, 인증되면 백에서 알아서 oAuth DB에 넣어주고
    // 리액트는 true 받으면 on/off 토글 변화
    // {platform : "", email: ""}
    @GetMapping("/snsLinking")
    public void linking(HttpSession session, @RequestParam("platform") String platform, @RequestParam("email") String email) {

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
    public @ResponseBody Map<String, Boolean> signup(HttpServletRequest request, HttpSession tmpSession, @RequestBody User user) {
        try {
            signup.signup(user);
            // signup.linking
            String snsTmpPlatform = tmpSession.getAttribute("platform").toString();
            String snsTmpEmail = tmpSession.getAttribute("snsEmail").toString();
            System.out.println(tmpSession.getAttribute("platform").toString() + tmpSession.getAttribute("snsEmail").toString());

            //session 파기
            System.out.println("세션 파기");
            tmpSession.invalidate();

            HttpSession session = request.getSession();

            session.setAttribute("userId", user.getId());
            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("userNickName", user.getNickName());

            // 새로운 seeion 생성 후 로그인
            System.out.println(session.getAttribute("userId").toString() + session.getAttribute("userEmail").toString());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonMap("result", false);
        }
        return Collections.singletonMap("result", true);
    }
}
