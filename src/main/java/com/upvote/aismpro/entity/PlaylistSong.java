package com.upvote.aismpro.entity;

import com.upvote.aismpro.entitypk.PlaylistSongPK;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "playlist_song")
@Data
@IdClass(PlaylistSongPK.class)
public class PlaylistSong implements Persistable<PlaylistSongPK> {
    @Id
    @Column(name = "playlistId", nullable = false)
    private Long playlistId;

    @Id
    @Column(name = "songId", nullable = false)
    private Long songId;

    @Transient
    private boolean isNew = true;

    public PlaylistSong(Long playlistId, Long songId) {
        this.playlistId = playlistId;
        this.songId = songId;
    }

    @Override
    public PlaylistSongPK getId() {
        return new PlaylistSongPK(playlistId, songId);
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
