package com.upvote.aismpro.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
;import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@Table(name = "playlist")
@Data
public class PlayList {
    @Id
    @Column(name = "playlistId", nullable = false)
    private String playlistId;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="creatorId")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "playlist_song",
            joinColumns = @JoinColumn(name = "playlistId", referencedColumnName="playlistId"),
            inverseJoinColumns = @JoinColumn(name = "songId", referencedColumnName="songId")
    )
    private List<Song> songs = new ArrayList<>();

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String img;
}
