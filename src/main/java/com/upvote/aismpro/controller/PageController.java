package com.upvote.aismpro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class PageController {

    @GetMapping("/hello")
    public String hello() {
        System.out.println("hi");
        return "Hi 현재 서버 시간 : " + new Date() + "입니다. \n";
    }

//    @PostMapping(value = "tokenVerify")
//    public ResponseEntity<?> tokenVerify(String idToken){
//        System.out.println("RequestBody value : " + idToken);
//
//        return null;
//    }

}
