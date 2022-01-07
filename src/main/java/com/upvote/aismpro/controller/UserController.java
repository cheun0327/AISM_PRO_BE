package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.ArtistDetailDTO;
import com.upvote.aismpro.dto.UserDTO;
import com.upvote.aismpro.service.SignupService;
import com.upvote.aismpro.service.UserService;
import net.bytebuddy.pool.TypePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.Response;
import java.io.File;
import java.io.IOException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private SignupService accountService;

    // 아티스트 디테일 정보 가져오기
    @GetMapping("/user/detail/{userID}")
    public ResponseEntity<ArtistDetailDTO> getArtistDetailInfo(@PathVariable("userID") Long userID) {
        try {
            return ResponseEntity.ok(userService.getArtistDetailInfo(userID));
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 이메일 중복 확인
    @GetMapping("/user/email/validate/{email}")
    public ResponseEntity<Boolean> emailDoubleCheck(@PathVariable("email") String email) {
        try {
            userService.emailDoubleCheck(email);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

    // 닉네임 중복 확인
    @GetMapping("/user/nickname/validate/{nickname}")
    public ResponseEntity<Boolean> nicknameDoubleCheck(@PathVariable("nickname") String nickname) {
        try {
            userService.nicknameDoubleCheck(nickname);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

    // user 정보 업데이트
    @PatchMapping("/user")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user) {
        try {
            UserDTO updateUser = userService.updateUser(user.getUserId(), user);
            return new ResponseEntity<>(updateUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // user 프로필 이미지 업로드
    @PostMapping("/user/profile/{userId}")
    public ResponseEntity<UserDTO> uploadProfileImg(@PathVariable("userId") Long userId,
                                                    @RequestParam("file") MultipartFile file) {
        try{
            UserDTO updateUser = userService.uploadUserProfile(userId, file);
            return new ResponseEntity<>(updateUser, HttpStatus.OK);
        } catch(IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
