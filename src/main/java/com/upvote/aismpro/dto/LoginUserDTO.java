package com.upvote.aismpro.dto;

import com.upvote.aismpro.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginUserDTO {
    private String token;
    private String userId;
    private String email;
    private String nickName;
    private String platform;

    public LoginUserDTO(String token, User user) {
        this.token = token;
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
    }

    public LoginUserDTO(String token, User user, String platform) {
        this.token = token;
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.platform = platform;
    }
}