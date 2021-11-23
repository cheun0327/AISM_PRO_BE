package com.upvote.aismpro.service;


import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.LoginUserDTO;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.UserRepository;
import com.upvote.aismpro.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.io.File;
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