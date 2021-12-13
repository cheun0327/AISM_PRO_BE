package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.LoginUserDTO;
import com.upvote.aismpro.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SignupController {

    @Autowired
    private SignupService accountService;

    @PostMapping("/signup/{nickname}")
    public ResponseEntity<LoginUserDTO> signup(HttpServletRequest request,
                                               @PathVariable("nickname") String nickname,
                                               @PathVariable("file") MultipartFile file) {
        try {
            LoginUserDTO loginUserDTO = accountService.signup(request.getSession(), nickname, file);
            return new ResponseEntity<>(loginUserDTO, HttpStatus.OK);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/signup/no-profile/{nickname}")
    public ResponseEntity<LoginUserDTO> signupWithoutProfile(HttpServletRequest request,
                                                             @PathVariable("nickname") String nickname) {
        try {
            LoginUserDTO loginUserDTO = accountService.signupWithoutProfile(request.getSession(), nickname);
            return new ResponseEntity<>(loginUserDTO, HttpStatus.OK);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
