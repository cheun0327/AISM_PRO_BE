package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.OAuth;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.OAuthRepository;
import com.upvote.aismpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class LoginService implements LoginServiceInter{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OAuthRepository oAuthRepository;

    // sns 로그인 완료시 받은 데이터로 연동 되었는지 확인
    @Override
    public String snsLinkageCheck(String platform, String email) throws EntityNotFoundException{
        try {
            OAuth findUser = (OAuth) oAuthRepository.findByPlatformAndEmail(platform, email);
            return findUser.getUserId();
        } catch (Exception e){
            throw new EntityNotFoundException("해당 sns 연동 정보가 없습니다.");
        }
    }

}
