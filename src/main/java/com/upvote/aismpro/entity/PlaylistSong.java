package com.upvote.aismpro.entity;

import com.upvote.aismpro.entitypk.PlaylistSongPK;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "playlist_song")
@Data
@IdClass(PlaylistSongPK.class)
public class PlaylistSong {
    @Id
    @Column(name="playlistId", nullable = false)
    private Long playlistId;

    @Id
    @Column(name="songId", nullable = false)
    private Long songId;
}
