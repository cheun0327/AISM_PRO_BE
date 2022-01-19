package com.upvote.aismpro.security;

import com.upvote.aismpro.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 5;        // 5시간
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;   // 7일

    @Value("${jwt.secret}")
    private String secreteKey;

    private Key key;

    private final UserService userService;

    @PostConstruct
    void init() {
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

        // SecurityContext 사용하기 위한 security 제공 객체 생성
        return new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                Collections.singletonList(new SimpleGrantedAuthority(claims.get(AUTHORITIES_KEY).toString()))
        );
    }

    // 검증
    public boolean validateToken(String token) {
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = jws.getBody();

            if (!StringUtils.hasText(claims.getSubject())) {
                throw new MalformedJwtException("sub does not exist");
            }

            if (Objects.isNull(claims.get(AUTHORITIES_KEY))) {
                throw new MalformedJwtException("auth does not exist");
            }

            return true;
        } catch (MalformedJwtException e) {
            System.out.println("잘못된 JWT payload");
            return false;
        } catch (SignatureException e) {
            System.out.println("잘못된 JWT 시그니쳐");
            return false;
        } catch (ExpiredJwtException e) {
            System.out.println("만료된 JWT 토큰");
            return false;
        } catch (UnsupportedJwtException e) {
            System.out.println("지원되지 않는 JWT 토큰");
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("잘못된 JWT 토큰");
            return false;
        }
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
