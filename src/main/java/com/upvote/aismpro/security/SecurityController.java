package com.upvote.aismpro.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
public class SecurityController {
    @Autowired
    private SecurityService securityService;

    @PostMapping("/createToken")
    public Map<String, Object> createToken(@RequestBody JwtRequestDTO jwtRequest) {
        String token = securityService.createToken(jwtRequest.getUserId());
        return Collections.singletonMap("token", token);
    }

    @GetMapping("/validateToken")
    public Map<String, Object> validateToken(@RequestParam("token") String token,
                                             @RequestParam("subject") String subject) {
        return Collections.singletonMap("isValid", securityService.validateToken(token, subject));
    }

    @GetMapping("/getSubject")
    // header에서 token 읽어오게 변경
    public Map<String, Object> getSubject(@RequestParam("token") String token) {
        String subject = securityService.getSubject(token);
        return Collections.singletonMap("subject", subject);
    }
}
