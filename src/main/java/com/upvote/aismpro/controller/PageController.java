package com.upvote.aismpro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class PageController {

    @GetMapping("/hello")
    public String hello() {
        return "Hi 현재 서버 시간 : " + new Date() + "입니다. \n";
    }
}
