package com.upvote.aismpro.entity;

import javax.persistence.*;

@Entity
@IdClass(AlbumPK.class)
public class Album {
    @Id
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "songId")
    private Song song;

    @Column(nullable = false)
    private String authority;
}
