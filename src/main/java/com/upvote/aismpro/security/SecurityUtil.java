package com.upvote.aismpro.security;

import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor
public class SecurityUtil {

    // SecurityContext에 user 정보 저장
    // Request가 들어올 때 JWTFilter의 doFilter에서 저장
    public static Long getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("SecurityContext에 인증 정보가 없습니다.");
        }

        System.out.println("토큰 추출 아이디");
        System.out.println(authentication.getName() + " / " + authentication.getPrincipal() + " / " + authentication.getCredentials());

        if (authentication.getName().equals("anonymousUser")) return Long.parseLong("-1");
        return Long.parseLong(authentication.getName());
    }
}

// name을 userId로 넣고
// password를 email로 설정하기

