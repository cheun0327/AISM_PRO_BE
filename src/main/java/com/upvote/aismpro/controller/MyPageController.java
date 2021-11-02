package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.UserDTO;
import com.upvote.aismpro.entity.OAuth;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class MyPageController {

    @Autowired
    private MyPageService mypageService;

    @PatchMapping("/user")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user) {
        try {
            UserDTO updateUser = mypageService.updateUser(user.getUserId(), user);
            return new ResponseEntity<>(updateUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 계정연동
    @PostMapping("/mypage/sns")
    public void linkSns(@RequestBody OAuth oauth) {
        mypageService.linkSns(oauth);
    }
}
