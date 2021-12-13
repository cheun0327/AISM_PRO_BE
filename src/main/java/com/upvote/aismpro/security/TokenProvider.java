package com.upvote.aismpro.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.http.auth.AUTH;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 5;        // 5시간
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;   // 7시간

    private final Key key;

    public TokenProvider(@Value("${jwt.secret}") String secreteKey) {
//        byte[] keyBytes = Decoders.BASE64.decode(secreteKey);
//        this.key = Keys.hmacShaKeyFor(keyBytes);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] keyBytes = DatatypeConverter.parseBase64Binary(secreteKey);
        this.key = new SecretKeySpec(keyBytes, signatureAlgorithm.getJcaName());
    }

    // 암호화
    public TokenDTO generateTokenDTO(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // TokenDTO 리턴
        return TokenDTO.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiration(accessTokenExpiresIn)
                .build();
    }

    // 복호화
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        // 권한 확인
        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 없는 토큰입니다.");
        }

        Collection<? extends  GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // spring security 자체 클래스 UserDetail, User
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        // SecurityContext 사용하기 위한 security 제공 객체 생성
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 검증
    public boolean validateToken(String token) throws IllegalAccessException {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            System.out.println(e.getMessage());
            System.out.println("잘못된 JWT 서명");
            throw new IllegalAccessException("잘못된 JWT 서명");
        } catch (ExpiredJwtException e) {
            System.out.println("만료된 JWT 토큰");
        } catch (UnsupportedJwtException e) {
            System.out.println("지원되지 않는 JWT 토큰");
        } catch (IllegalArgumentException e) {
            System.out.println("잘못된 JWT 토큰");
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
