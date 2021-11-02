package com.upvote.aismpro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private String userId;
    private String email;
    private String nickName;
    private String platform;
}
