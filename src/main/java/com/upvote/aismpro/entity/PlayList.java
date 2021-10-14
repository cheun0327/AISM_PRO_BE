package com.upvote.aismpro.entity;

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
//@IdClass(PlayListPK.class)
public class PlayList {
    @Id
    @Column(name = "playlistId", nullable = false)
    private String playlistId;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

//    @Column(nullable = false)
//    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String img;

    @ManyToMany
    @JoinTable(name = "playlist_song",
        joinColumns = @JoinColumn(name = "playlistId", referencedColumnName="playlistId"),
        inverseJoinColumns = @JoinColumn(name = "songId", referencedColumnName="songId")
    )
    private List<Song> song = new ArrayList<>();
}
