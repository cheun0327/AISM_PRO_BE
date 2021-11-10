package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.UserDTO;
import com.upvote.aismpro.entity.User;

import java.util.Optional;
import javax.persistence.EntityNotFoundException;

public interface LoginServiceInter {

    // 사용자 정보 가져오기
    public User getUserInfo(String userID);
    // sns 연동 확인
    public String snsLinkageCheck(String platform, String email) throws Exception;
    // user 가져오기
}
