package com.upvote.aismpro.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    AuthService authService;

    @GetMapping("/auth")
    public Map<String, Object> sessionCheck(HttpServletRequest request) {
        try{
            HttpSession session = request.getSession(false);
            System.out.println(session);
            return session != null ? Collections.singletonMap("result", true) : Collections.singletonMap("result", false);
        }catch(Exception e){
            e.printStackTrace();
            return Collections.singletonMap("result", false);
        }
    }
}
