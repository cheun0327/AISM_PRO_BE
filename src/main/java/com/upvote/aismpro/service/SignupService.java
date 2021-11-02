package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.OAuth;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.OAuthRepository;
import com.upvote.aismpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SignupService implements SignupServiceInter{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OAuthRepository oAuthRepository;


    @Override
    public void signup(User user) throws Exception{
        try {
            List<User> users = userRepository.findAllByPlatformAndEmail(user.getPlatform(), user.getEmail());
            if (users.size() >= 1)  throw new IllegalAccessException();
            userRepository.save(user);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("새로운 User 등록에 실패하였습니다.");
        }
    }

    @Override
    public User signupParam(String nickName, String email, String platform) throws Exception {
        try {
            User user = new User(
                    createRandomId(), nickName, email, platform
            );
            userRepository.save(user);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("새로운 User 등록에 실패하였습니다.");
        }
    }

    @Override
    public void linking(String userId, String platform, String email) throws Exception{
        try {
            OAuth oAuth = new OAuth();
            oAuth.setUserId(userId);
            oAuth.setPlatform(platform);
            oAuth.setEmail(email);
            oAuthRepository.save(oAuth);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("oAuth 등록에 실패하였습니다.");
        }
    }

    @Override
    public void emailDoubleCheck(String email) {
        List<User> findUsers = userRepository.findByEmail(email);
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }

    @Override
    public void nickDoubleCheck(String nickName) throws Exception {
        List<User> findUsers = userRepository.findByNickName(nickName);
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }

    private String createRandomId() {
        return UUID.randomUUID().toString();
    }

}
