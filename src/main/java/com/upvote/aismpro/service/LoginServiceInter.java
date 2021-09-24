package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.User;

import java.util.Optional;
import javax.persistence.EntityNotFoundException;

public interface LoginServiceInter {

    // 닉네임 중복 확인
    public void nickDoubleCheck(String nickName);
    // 회원가입 실행
    public void signup(User input) throws Exception;

    // 사용자 정보 가져오기
    public Optional<User> getUserInfo(String userID);

    // sns 연동 확인
    public String snsLinkageCheck(String platform, String email) throws EntityNotFoundException;
}
