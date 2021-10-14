package com.upvote.aismpro.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "my_album")
@Data
@IdClass(MyAlbumPK.class)
public class MyAlbum {
    @Id
    @Column(nullable = false)
    private String userId;

    @Id
    @Column(nullable = false)
    private String songId;


//    @ManyToOne
//    @JoinColumn(name = "userId")
//    private User user;
//
//
//    @OneToOne
//    @JoinColumn(name = "songId")
//    private Song song;

    @Column(nullable = false)
    private String authority;
}
