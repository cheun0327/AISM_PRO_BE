package com.upvote.aismpro.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "playlist_song")
@Data
@IdClass(PlayListSongPK.class)
public class PlayListSong {
    @Id
    @Column(nullable = false)
    private String playlistId;

    @Id
    @Column(nullable = false)
    private String songId;
}