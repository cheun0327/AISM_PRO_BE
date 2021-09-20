package com.upvote.aismpro.controller;

<<<<<<< HEAD
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
=======
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
>>>>>>> 275624c5921e74750e9f44787305b1380c0b2756

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@JsonComponent
public class PageController  {

    private Object MapUtils;

<<<<<<< HEAD
    @GetMapping("/hello")
    public String hello() {
        System.out.println("hi");
        System.out.println("hi");
        return "Hi 현재 서버 시간 : " + new Date() + "입니다. \n";
    }


=======
>>>>>>> 275624c5921e74750e9f44787305b1380c0b2756
}
