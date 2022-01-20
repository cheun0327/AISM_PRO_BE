package com.upvote.aismpro.security;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
// API요청 마다 한 번 실행되는 필터
public class JWTFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;

    // JWT 토큰 정보를 현재 스레드의 SecurityContext에 저장함
    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws IOException, ServletException {
        // 토큰 추출
        String jwt = resolveToken(request);

        // 토큰 검증하고 올바르면, SecurityContext에 저장
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    // Request Header의 토큰 정보 전처리
    private String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        // Authorization 헤더가 없을 경우
        if (!StringUtils.hasText(bearerToken)) {
            return null;
        }

        // "Bearer " 문자열 이후 토큰이 없을 경우
        if (!StringUtils.hasText(bearerToken.replace(BEARER_PREFIX.trim(), ""))) {
            return null;
        }

        return bearerToken.substring(7);
    }
}
