package com.upvote.aismpro.customrepository;

import com.upvote.aismpro.entity.PlayList;
import com.upvote.aismpro.entity.PlayListSong;

import java.util.List;

public interface PlaylistSongRepositoryCustom {
    public List<PlayListSong> findSavedPlaylistBySongId(String songId);
}
