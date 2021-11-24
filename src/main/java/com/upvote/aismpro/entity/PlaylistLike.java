package com.upvote.aismpro.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "playlist_like")
@NoArgsConstructor
public class PlaylistLike {

    @Id
    @Column(name = "playlistLikeId")
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "playlistId")
    private Playlist playlist;

    public PlaylistLike(User user, Playlist playlist) {
        this.user = user;
        this.playlist = playlist;
    }
}
