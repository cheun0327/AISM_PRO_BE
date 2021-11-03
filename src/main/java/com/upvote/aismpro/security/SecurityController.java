package com.upvote.aismpro.security;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.LoginUserDTO;
import com.upvote.aismpro.dto.UserDTO;
import com.upvote.aismpro.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SecurityController {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private CustomModelMapper modelMapper;

    @PostMapping("/token")
    public Map<String, Object> createToken(@RequestBody JwtRequestDTO jwtRequest) {
        String token = securityService.createToken(jwtRequest);
        return Collections.singletonMap("token", token);
    }

    @GetMapping("/token/validate/{token}")
    public Map<String, Object> validateToken(@PathVariable("token") String token) {
        System.out.println(token);
        try {
            System.out.println(token);
            Boolean isValid = securityService.validateToken(token);
            System.out.println(isValid);
        } catch (ExpiredJwtException jwtE) {
            jwtE.printStackTrace();
            return Collections.singletonMap("isValid", false);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonMap("isValid", false);
        }
        return Collections.singletonMap("isValid", securityService.validateToken(token));
    }

    @GetMapping("/token/validate/subject/{token}")
    public Map<String, Object> validateTokenWithSubject(@PathVariable("token") String token,
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

    @GetMapping("/token/user/{token}")
    public ResponseEntity<UserDTO> getUserByToken(@PathVariable("token") String token) {
        User user = securityService.getUser(token);
        return new ResponseEntity<>(modelMapper.userMapper().map(user, UserDTO.class), HttpStatus.OK);
    }

    @GetMapping("/token/subject/{token}")
    // header에서 token 읽어오게 변경
    public Map<String, Object> getSubject(@PathVariable("token") String token) {
        String subject = securityService.getSubject(token);
        return Collections.singletonMap("subject", subject);
    }
}
