package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.OAuth;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.OAuthRepository;
import com.upvote.aismpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyPageService implements MyPageServiceInter{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OAuthRepository oAuthRepository;

    public User updateUser(String id, User user) {
        try {
            final User fetchedUser = userRepository.findById(id).get();

            if (user.getEmail() != null)     fetchedUser.setEmail(user.getEmail());
            if (user.getNickName() != null)  fetchedUser.setNickName(user.getNickName());
            userRepository.save(fetchedUser);

            return fetchedUser;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<OAuth> linkSns(OAuth oauth) {
        List<OAuth> oauthList = new ArrayList<OAuth>();
        try {
            oAuthRepository.save(oauth);

            return oauthList;
        } catch(Exception e){
            e.printStackTrace();
            return oauthList;
        }
    }

//    public User putUser(User user) {
//        userRepository.put()
//    }
}
