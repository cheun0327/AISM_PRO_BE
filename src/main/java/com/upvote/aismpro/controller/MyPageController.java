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

    // 고민 :
    // 리엑트에서 user 전체 데이터를 꽉 채워 보내서 userRepo.save(updateUser)로 한번에 변경할지
    // 변경할 값만 requestbody에 넣어 보내서 특정 값만 변경할지..
    // 결론 : @DynamicUpdate를 쓰든 null 값 아닌거만 찾아서 업데이트하든 오버헤드.. -> put으로!
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
