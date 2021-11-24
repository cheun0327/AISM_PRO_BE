package com.upvote.aismpro.customrepository;

import com.upvote.aismpro.entity.PlaylistSong;

import java.util.List;

public interface PlaylistSongRepositoryCustom {
    public List<PlaylistSong> findPlaylistBySongIdQD(Long songId);
}
