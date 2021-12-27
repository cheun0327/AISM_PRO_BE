package com.upvote.aismpro.service;


import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomModelMapper modelMapper;

    public User checkUser(String platform, String email) throws Exception {

        List<User> users = userRepository.findAllByPlatformAndEmail(platform, email);

        if (users.isEmpty()) throw new NoSuchElementException();
        if (users.size() > 1) throw new IllegalAccessException();

        return users.get(0);
    }

    // 사용자 정보 가져오기
    public User getUserInfo(Long userID) throws EntityNotFoundException{
        return userRepository.findById(userID)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
    }
}