package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.UserDTO;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserServiceInter {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomModelMapper modelMapper;

    @Override
    @Transactional
    public UserDTO setProfile(String userId, String imgName){
        User user = userRepository.getById(userId);
        user.setProfile(imgName);
        userRepository.save(user);
        return modelMapper.userMapper().map(user, UserDTO.class);
    }
}
