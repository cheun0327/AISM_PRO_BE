package com.upvote.aismpro.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "playlist")
@Data
@IdClass(PlayListPK.class)
public class PlayList {
    @Id
    @Column(nullable = false)
    private String playlistId;

    @Id
    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String img;
}
