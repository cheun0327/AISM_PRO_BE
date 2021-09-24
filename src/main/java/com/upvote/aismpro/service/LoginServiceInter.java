package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.User;

public interface LoginServiceInter {

    // 닉네임 중복 확인
    public void nickDoubleCheck(String nickName);
    // 회원가입 실행
    public void signup(User input) throws Exception;

}
