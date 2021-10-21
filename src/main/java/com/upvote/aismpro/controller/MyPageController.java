package com.upvote.aismpro.controller;

import com.upvote.aismpro.entity.OAuth;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class MyPageController {

    @Autowired
    private MyPageService mypageService;

    @PatchMapping("/mypage/{id}")
    public User updateUser(@PathVariable(value = "id") String id, @RequestBody User user) {
        return mypageService.updateUser(id, user);
    }

    // 계정연동
    @PostMapping("/mypage/sns")
    public void linkSns(@RequestBody OAuth oauth) {
        mypageService.linkSns(oauth);
    }
}
