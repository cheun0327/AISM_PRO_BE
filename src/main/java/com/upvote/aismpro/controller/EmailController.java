package com.upvote.aismpro.controller;

import com.upvote.aismpro.service.EmailService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
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
    //@GetMapping("/email") // 이메일 인증 코드 보내기
    public ResponseEntity<String> emailAuth(@RequestBody Map<String, String> email) throws Exception {
    //public ResponseEntity<String> emailAuth(@RequestParam String email) throws Exception {
        System.out.println("이메일 컨트롤러" + email + "///" + email.get("email"));
        emailService.sendSimpleMessage(email.get("email"));
        //emailService.sendSimpleMessage(email);

        return new ResponseEntity<String>("true", HttpStatus.OK);
    }

    @PostMapping("/verifyCode") // 이메일 인증 코드 검증
    public ResponseEntity<String> verifyCode(@RequestBody Map<String, String> code) {
        if(emailService.getEPW().equals(code.get("code"))) {
            System.out.println("코드 인증 완료");
            return new ResponseEntity<String>("true", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("false", HttpStatus.OK);
        }
    }
}