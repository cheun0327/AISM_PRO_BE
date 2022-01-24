package com.upvote.aismpro.entity;

import com.upvote.aismpro.entitypk.PlaylistSongPK;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@IdClass(PlaylistSongPK.class)
@Table(name = "playlist_song")
@Entity
public class PlaylistSong implements Persistable<PlaylistSongPK> {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlistId", referencedColumnName = "playlistId", nullable = false)
    private Playlist playlist;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "songId", referencedColumnName = "songId", nullable = false)
    private Song song;

    @Transient
    private boolean isNew = true;

    public PlaylistSong(Playlist playlist, Song song) {
        this.playlist = playlist;
        this.song = song;
    }

    @Override
    public PlaylistSongPK getId() {
        return new PlaylistSongPK(playlist.getPlaylistId(), song.getSongId());
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    @PrePersist
    @PostLoad
    public void markNotNew() {
        this.isNew = false;
    }
}
