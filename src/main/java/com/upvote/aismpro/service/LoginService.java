package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginService {

    @Autowired
    private UserRepository user;

    public void signup(User input) {
        input.setId(createRandomId());
        user.save(input);
    }

    private String createRandomId() {
        String uniqueId = UUID.randomUUID().toString();
        return uniqueId;
    }

}
