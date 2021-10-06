package com.upvote.aismpro.security;

import lombok.Data;

@Data
public class JwtRequestDTO {
    private String userId;
    private String userEmail;
    private String userNickName;
}
