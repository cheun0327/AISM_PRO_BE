package com.upvote.aismpro.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "oAuth")
@Data
@NoArgsConstructor
@IdClass(OAuthPK.class)
public class OAuth {

    @Id
    private String userId;

    @Id
    private String platform;

    @Column(nullable = false)
    private String email;

}
