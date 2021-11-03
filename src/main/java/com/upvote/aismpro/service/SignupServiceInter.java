package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.LoginUserDTO;
import com.upvote.aismpro.entity.OAuth;
import com.upvote.aismpro.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

public interface SignupServiceInter {
    // 닉네임 중복 확인
    public void nickDoubleCheck(String nickName) throws Exception;
    // 이메일 중복 확인
    public void emailDoubleCheck(String email);
    // 회원가입 실행
    public LoginUserDTO signup(HttpSession session, String nickName, MultipartFile file) throws Exception;
    public LoginUserDTO signupWithoutProfile (HttpSession session, String nickName) throws Exception;
    // 연동 실행
    public void linking(String userId, String platform, String email) throws Exception;
}
