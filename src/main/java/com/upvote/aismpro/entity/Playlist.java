package com.upvote.aismpro.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "playlist")
@NoArgsConstructor
public class Playlist {
    @Id
    @Column(name = "playlistId", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long playlistId;

    @Column(nullable = false)
    private String name;

    @Column(name = "createDate")
    private Timestamp createDate;

    @Column
    private Boolean state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="creatorId", referencedColumnName="userId")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "playlist_song",
            joinColumns = @JoinColumn(name = "playlistId", referencedColumnName="playlistId"),
            inverseJoinColumns = @JoinColumn(name = "songId", referencedColumnName="songId")
    )
    private List<Song> songs;

    @Column(name = "one")
    private String one;

    @Column(name="two")
    private String two;

    @Column(name="three")
    private String three;

    @Column(name = "imgFile")
    private String imgFile;
}
