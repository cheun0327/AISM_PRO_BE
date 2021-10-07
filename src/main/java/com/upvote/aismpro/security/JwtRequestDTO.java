package com.upvote.aismpro.security;

import lombok.Data;

@Data
public class JwtRequestDTO {
    private String userId;
    private String userEmail;
    private String userNickName;

    public JwtRequestDTO() {
    }
    public JwtRequestDTO(String userId, String userEmail, String userNickName) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userNickName = userNickName;
    }
}
