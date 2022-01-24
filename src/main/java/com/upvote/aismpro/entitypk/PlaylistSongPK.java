package com.upvote.aismpro.entitypk;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class PlaylistSongPK implements Serializable {
    private Long playlist;
    private Long song;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaylistSongPK that = (PlaylistSongPK) o;
        return playlist.equals(that.playlist) && song.equals(that.song);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlist, song);
    }
}
