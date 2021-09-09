package com.upvote.aismpro.controller;


import org.springframework.boot.jackson.JsonComponent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@JsonComponent
public class LoginController {
    @PostMapping(value = "tokenVerify")
    public ResponseEntity<?> tokenVerify(Object data){
        System.out.println("RequestBody value : " + data);
        return null;
    }
}
