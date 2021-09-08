package com.upvote.aismpro.security;

import com.upvote.aismpro.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;

    @Builder
    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
}