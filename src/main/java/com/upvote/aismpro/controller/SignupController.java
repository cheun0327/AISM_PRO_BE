package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.LoginUserDTO;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.security.SecurityService;
import com.upvote.aismpro.service.SignupService;
import com.upvote.aismpro.service.SignupServiceInter;
import com.upvote.aismpro.service.UserServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
public class SignupController {
    @Autowired
    private SignupService signupService;

    @PostMapping("/signup/{nickName}")
    public ResponseEntity<LoginUserDTO> signup(HttpServletRequest request,
                                               @PathVariable("nickName") String nickName,
                                               @RequestParam("file") MultipartFile file) {
        try {
            LoginUserDTO loginUserDTO = signupService.signup(request.getSession(), nickName, file);
            return new ResponseEntity<>(loginUserDTO, HttpStatus.OK);
        } catch (IllegalAccessException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signup/no-profile/{nickName}")
    public ResponseEntity<LoginUserDTO> signupWithoutProfile(HttpServletRequest request,
                                               @PathVariable("nickName") String nickName) {
        try {
            LoginUserDTO loginUserDTO = signupService.signupWithoutProfile(request.getSession(), nickName);
            return new ResponseEntity<>(loginUserDTO, HttpStatus.OK);
        } catch (IllegalAccessException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
