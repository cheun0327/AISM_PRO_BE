package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        userRepo.findAll().forEach(e -> users.add(e));
        return users;
    }

    public Optional<User> findById(String userId) {
        Optional<User> user = userRepo.findByUserId(userId);
        return user;
    }

    public User save(User user) {
        userRepo.save(user);
        return user;
    }

    public void updateById(String userId, User user) {
        Optional<User> e = userRepo.findByUserId(userId);

//        if (e.isPresent()) {
//            e.get().setMbrNo(user.getMbrNo());
//            e.get().setId(user.getId());
//            e.get().setName(user.getName());
//            userRepo.save(user);
//        }
    }

}
