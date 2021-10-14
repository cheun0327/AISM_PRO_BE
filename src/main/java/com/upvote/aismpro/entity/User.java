package com.upvote.aismpro.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "user")
@Data
public class User {

    @Id
    @Column(name = "userId", nullable = false)
    private String id;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String email;

    @Builder
    public User(String nickName, String email, String picture) {
        this.nickName = nickName;
        this.email = email;
    }

    public User update(String nickName, String email) {
        this.nickName = nickName;
        this.email = email;

        return this;
    }
}
