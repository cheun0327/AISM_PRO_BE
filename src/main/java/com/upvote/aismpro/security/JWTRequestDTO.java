package com.upvote.aismpro.security;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JWTRequestDTO {
    private Long userId;
    private String userEmail;
    private String userNickname;

    public JWTRequestDTO(Long userId, String userEmail, String userNickname) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userNickname = userNickname;
    }
}
