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

    private final EmailService emailService;

    //@PostMapping("/email")
    @GetMapping("/email") // 이메일 인증 코드 보내기
    //public ResponseEntity<String> emailAuth(@RequestBody Map<String, String> email) throws Exception {
    public ResponseEntity<String> emailAuth(@RequestParam String email) throws Exception {
        //emailService.sendSimpleMessage(email.get("email"));
        emailService.sendSimpleMessage(email);

        return new ResponseEntity<String>("true", HttpStatus.OK);
    }

    @PostMapping("/verifyCode") // 이메일 인증 코드 검증
    public ResponseEntity<String> verifyCode(@RequestBody Map<String, String> code) {
        if(EmailService.ePw.equals(code.get("code"))) {
            return new ResponseEntity<String>("true", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("false", HttpStatus.OK);
        }
    }
}