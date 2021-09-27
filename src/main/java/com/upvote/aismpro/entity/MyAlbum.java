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

    @Column(nullable = false)
    private String authority;
}
