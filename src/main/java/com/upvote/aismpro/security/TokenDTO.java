package com.upvote.aismpro.security;

import lombok.Builder;
import java.util.Date;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class TokenDTO {
    private String grantType;

    private String accessToken;

    private String refreshToken;

    private Date expiration;
}
