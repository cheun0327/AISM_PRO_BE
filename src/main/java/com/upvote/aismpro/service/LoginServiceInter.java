package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.User;

import javax.persistence.EntityNotFoundException;

public interface LoginServiceInter {

    // sns 연동 확인
    public String snsLinkageCheck(String platform, String email) throws EntityNotFoundException;

}
