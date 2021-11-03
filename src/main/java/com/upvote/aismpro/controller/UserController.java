package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.UserDTO;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.UserRepository;
import com.upvote.aismpro.service.MyPageService;
import com.upvote.aismpro.service.SignupServiceInter;
import com.upvote.aismpro.service.UserServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RestController
public class UserController {

    @Autowired
    private MyPageService mypageService;

    @Autowired
    private SignupServiceInter signupService;

    @Autowired
    private UserServiceInter userService;


    // 이메일 중복 확인
    @GetMapping("/user/email/validate/{email}")
    public @ResponseBody
    Map<String, Boolean> emailDoubleCheck(@PathVariable("email") String email) {
        System.out.println("== email Double Check : " + email);
        try {
            signupService.emailDoubleCheck(email);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return Collections.singletonMap("result", false);
        }
        return Collections.singletonMap("result", true);
    }

    // 닉네임 중복 확인
    @GetMapping("/user/nickname/validate/{nickName}")
    public ResponseEntity<Boolean> nickDoubleCheck(@PathVariable("nickName") String nickName) {
        System.out.println("== nickName Double Check : " + nickName);
        try {
            signupService.nickDoubleCheck(nickName);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // user 정보 업데이트
    @PatchMapping("/user")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user) {
        try {
            UserDTO updateUser = mypageService.updateUser(user.getUserId(), user);
            return new ResponseEntity<>(updateUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user/img/{userId}")
    public ResponseEntity<UserDTO> uploadProfileImg(
            @PathVariable("userId") String userId,
            @RequestParam("file") MultipartFile file) throws IOException {

        String[] imgNameArr = file.getOriginalFilename().split("\\.");
        String imgFolder = userId.replaceAll("-","");
        String imgName = imgFolder + "." +imgNameArr[imgNameArr.length - 1];
//        String dirPath = "/Users/upvote3/chaeeun/dev/react-workspace/AISM_PRO_FE/src/components/content/image/user/" + imgFolder;
        String dirPath = "/var/lib/jenkins/workspace/AISM_PRO_REACT/src/components/content/image/user/" + imgFolder;
        File profileDir = new File(dirPath);


        try {
            if (!profileDir.exists()) {
                profileDir.mkdir();
            }
            else {
                File[] files= profileDir.listFiles(); //파일리스트 얻어오기
                for (File f : files) f.delete(); //파일 삭제
            }

            file.transferTo(new File(dirPath + "/" + imgName));
            UserDTO user = userService.setProfile(userId, imgFolder + "/" + imgName);

            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
