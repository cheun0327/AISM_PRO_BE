package com.upvote.aismpro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    private String email;
    private String nickname;
    private String platform;
    private String profile;
    private Long credit;
}
