package com.upvote.aismpro.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "favor")
@Data
@IdClass(FavorPK.class)
public class Favor {
    @Id
    @Column(nullable = false)
    private String userId;

    @Id
    @Column(nullable = false)
    private String songId;
}
