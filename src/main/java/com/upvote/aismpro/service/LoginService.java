package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.OAuth;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.OAuthRepository;
import com.upvote.aismpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoginService implements LoginServiceInter{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OAuthRepository oAuthRepository;

    public User checkUser(String platform, String email) throws Exception {

        List<User> users = userRepository.findAllByPlatformAndEmail(platform, email);
        if (users.isEmpty()) throw new NoSuchElementException();
        if (users.size() > 1) throw new IllegalAccessException();
        return users.get(0);
    }

    // sns 로그인 완료시 받은 데이터로 연동 되었는지 확인
    @Override
    public String snsLinkageCheck(String platform, String email) throws EntityNotFoundException{
        OAuth findUser = oAuthRepository.findByPlatformAndEmail(platform, email)
                .orElseThrow(() -> new EntityNotFoundException("linkage not found"));
        return findUser.getUserId();
    }

    // 사용자 정보 가져오기
    @Override
    public User getUserInfo(String userID) throws EntityNotFoundException{
        return userRepository.findById(userID)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
    }

    private String createRandomId() {
        return UUID.randomUUID().toString();
    }


}
