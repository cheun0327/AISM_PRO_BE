package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.UserDTO;
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

    @Autowired
    private CustomModelMapper modelMapper;

    public UserDTO updateUser(String id, UserDTO user) throws Exception {
        try {
            final User fetchedUser = userRepository.findById(id).get();

            if (user.getEmail() != null)     fetchedUser.setEmail(user.getEmail());
            if (user.getNickName() != null)  fetchedUser.setNickName(user.getNickName());
            userRepository.save(fetchedUser);

            return modelMapper.userMapper().map(fetchedUser, UserDTO.class);
        } catch(Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }

    public List<OAuth> linkSns(OAuth oauth) {
        List<OAuth> oauthList = new ArrayList<>();
        try {
            oAuthRepository.save(oauth);
            return oauthList;
        } catch(Exception e){
            e.printStackTrace();
            return oauthList;
        }
    }

}
