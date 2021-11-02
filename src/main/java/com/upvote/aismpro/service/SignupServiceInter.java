package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.OAuth;
import com.upvote.aismpro.entity.User;

public interface SignupServiceInter {
    // 닉네임 중복 확인
    public void nickDoubleCheck(String nickName) throws Exception;
    // 이메일 중복 확인
    public void emailDoubleCheck(String email);
    // 회원가입 실행
    public void signup(User input) throws Exception;
    public User signupParam(String nickName, String email, String platform) throws Exception;
    // 연동 실행
    public void linking(String userId, String platform, String email) throws Exception;
}
