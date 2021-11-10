package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.UserDTO;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.Future;

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

    @Override
    public UserDTO getUserDTO(String userId) throws Exception {
        try{
            User user = userRepository.getById(userId);
            return modelMapper.userMapper().map(user, UserDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }
}
