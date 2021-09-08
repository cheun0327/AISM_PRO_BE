package com.upvote.aismpro.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Builder
    public User(String name, String email, String picture) {
        this.name = name;
        this.email = email;
    }

    public User update(String name, String email) {
        this.name = name;
        this.email = email;

        return this;
    }
}
