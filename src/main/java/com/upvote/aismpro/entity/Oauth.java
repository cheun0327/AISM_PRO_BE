package com.upvote.aismpro.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oAuth")
@Data
@NoArgsConstructor
public class Oauth {

    @Id
    private String userId;

    @Column(nullable = false)
    private String platform;

    @Column(nullable = false)
    private String email;

}
