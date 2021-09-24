package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.Oauth;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.OauthRepository;
import com.upvote.aismpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoginService implements LoginServiceInter{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OauthRepository oauthRepository;


    public void snsLogin(String platform, String email) {
        List<Oauth> findUsers =  oauthRepository.findByPlatformAndEmail(platform, email);

    }

    @Override
    public void signup(User user) throws Exception{
        try {
            user.setId(createRandomId());
            userRepository.save(user);
        } catch (Exception e) {
            throw new Exception("새로운 User 등록에 실패하였습니다.");
        }
    }

    @Override
    public void nickDoubleCheck(String nickName) {
        List<User> findUsers = userRepository.findByNickName(nickName);
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }

    // 사용자 정보 가져오기
    @Override
    public Optional<User> getUserInfo(String userID) {
        return userRepository.findById(userID);
    }

    private String createRandomId() {
        return UUID.randomUUID().toString();
    }

}
