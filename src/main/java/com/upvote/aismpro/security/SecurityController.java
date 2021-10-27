package com.upvote.aismpro.security;

import com.upvote.aismpro.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SecurityController {
    @Autowired
    private SecurityService securityService;

    @PostMapping("/token")
    public Map<String, Object> createToken(@RequestBody JwtRequestDTO jwtRequest) {
        String token = securityService.createToken(jwtRequest);
        return Collections.singletonMap("token", token);
    }

    @GetMapping("/token/validate")
    public Map<String, Object> validateToken(@RequestParam("token") String token) {
        System.out.println(token);
        try {
            Boolean isValid = securityService.validateToken(token);
        } catch (ExpiredJwtException jwtE) {
            return Collections.singletonMap("isValid", false);
        }
        return Collections.singletonMap("isValid", securityService.validateToken(token));
    }

    @GetMapping("/token/validate/subject")
    public Map<String, Object> validateTokenWithSubject(@RequestParam("token") String token,
                                             @RequestParam("subject") String subject) {
        System.out.println(token);
        System.out.println(subject);
        try {
            Boolean isValid = securityService.validateTokenWithSubject(token, subject);
        } catch (ExpiredJwtException jwtE) {
            return Collections.singletonMap("isValid", false);
        }
        return Collections.singletonMap("isValid", securityService.validateTokenWithSubject(token, subject));
    }

    @GetMapping("/token/user")
    public Map<String, String> getUserByToken(@RequestParam("token") String token) {
        User user = securityService.getUser(token);
        return new HashMap<String, String>() {{
            put("result", "success");
            put("userId", user.getId());
            put("userEmail", user.getEmail());
            put("userNickName", user.getNickName());
        }};
    }

    @GetMapping("/token/subject")
    // header에서 token 읽어오게 변경
    public Map<String, Object> getSubject(@RequestParam("token") String token) {
        String subject = securityService.getSubject(token);
        return Collections.singletonMap("subject", subject);
    }
}
