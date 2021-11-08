package com.upvote.aismpro.security;

import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.UserRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

@Service
public class SecurityService {
    private static final String SECRETE_KEY = "qohjkbdlxfklbskcnwpdkvnzkwjerlqkspfksnxnbcfghjghd";

    @Autowired
    private UserRepository userRepository;

    public String createToken(JwtRequestDTO jwtRequestDTO) {
        long expTime = 8*1000*60*60;// 10*1000*60*60; // 10 hour

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRETE_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        System.out.println("============= make key ===================");

        Claims claims = Jwts.claims().setSubject(jwtRequestDTO.getUserId());
        claims.put("userEmail", jwtRequestDTO.getUserEmail());
        claims.put("userNickName", jwtRequestDTO.getUserNickName());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(signingKey, signatureAlgorithm)
                .setExpiration(new Date(System.currentTimeMillis() + expTime))
                .compact();
    }

    public JwtRequestDTO transformUserToJwtRequestDto(User user) {
        JwtRequestDTO jwtRequestDTO = new JwtRequestDTO(user.getId(), user.getEmail(), user.getNickName());
        return jwtRequestDTO;
    }
    public Boolean validateToken(String token) {
        //final String userId = getUserIdFromToken(token);
        try{
            return !isTokenExpired(token);
        }catch (Exception e) {
            e.printStackTrace();
            return (false);
        }
    }

    public Boolean validateTokenWithSubject(String token, String subject) {
        final String userId = getUserIdFromToken(token);
        return (userId.equals(subject) && !isTokenExpired(token));
    }

    public User getUser(String token) {
        String userId = getSubject(token);
        User user = userRepository.getById(userId);
        return user;
    }

    public String getSubject(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRETE_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public String getUserIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRETE_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
