package com.upvote.aismpro.security;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
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

        // TODO 토큰 없이 요청 왔을 때 처리 방식 고민
        // header에 토큰 정보 있으면 가공해서 보내고, 아니면 null 처리
        if (StringUtils.hasText(bearerToken)) System.out.println("토큰 길이 : " + !bearerToken.substring(7).equals("null"));

        if (StringUtils.hasText(bearerToken) && !bearerToken.substring(7).equals("null") && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
