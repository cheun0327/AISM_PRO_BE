package com.upvote.aismpro.entitypk;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class PlaylistSongPK implements Serializable {
    private Long playlistId;
    private Long songId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaylistSongPK that = (PlaylistSongPK) o;
        return playlistId.equals(that.playlistId) && songId.equals(that.songId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlistId, songId);
    }
}
