package com.upvote.aismpro.dto;

import com.upvote.aismpro.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginUserDTO {
    private String token;
    private Long userId;
    private String email;
    private String nickname;
    private String platform;
    private String profile;

    public LoginUserDTO(String token, User user) {
        this.token = token;
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.platform = user.getPlatform();
        this.profile = user.getProfile();
    }

    public LoginUserDTO(String token, User user, String platform) {
        this.token = token;
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.platform = platform;
        this.profile = user.getProfile();
    }
}
