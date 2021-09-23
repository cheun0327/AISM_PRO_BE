package com.upvote.aismpro.controller;

import com.upvote.aismpro.service.EmailService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/email")
    public ResponseEntity<String> emailAuth(@RequestBody Map<String, String> email) throws Exception {
        emailService.sendSimpleMessage(email.get("email"));

        return new ResponseEntity<String>("true", HttpStatus.OK);
    }

    @PostMapping("/verifyCode") // 이메일 인증 코드 검증
    public ResponseEntity<String> verifyCode(@RequestBody Map<String, String> code) {
        System.out.println("code : "+code);
        if(emailService.getEPW().equals(code.get("code"))) {
            System.out.println("코드 인증 완료");

            return new ResponseEntity<String>("true", HttpStatus.OK);
        }
        else{
            System.out.println("코드 인증 실패");

            return new ResponseEntity<String>("false", HttpStatus.OK);
        }
    }
}