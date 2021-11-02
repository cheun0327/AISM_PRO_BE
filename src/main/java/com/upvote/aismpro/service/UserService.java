package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserServiceInter {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void setProfile(String userId, String imgName){
        User user = userRepository.getById(userId);
        user.setProfile(imgName);
        userRepository.save(user);
    }
}
