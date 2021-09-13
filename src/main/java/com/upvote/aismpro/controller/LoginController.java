package com.upvote.aismpro.controller;


import com.upvote.aismpro.entity.BookEntity;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.loginverifier.GoogleTokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
public class LoginController {

    @Autowired
    GoogleTokenVerifier googleVerifier;

    @PostMapping("/tokenVerify")
    //public ResponseEntity<User> tokenVerify(@RequestBody Map param){
    public void tokenVerify(@RequestBody Map param){
        System.out.println("RequestBody value : " + param.get("tokenId"));
        googleVerifier.tokenVerify((String) param.get("tokenId"));


        //return null;
    }


}
