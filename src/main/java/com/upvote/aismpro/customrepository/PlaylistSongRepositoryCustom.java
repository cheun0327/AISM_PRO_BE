package com.upvote.aismpro.customrepository;

import com.upvote.aismpro.entity.PlaylistSong;

import java.util.List;

public interface PlaylistSongRepositoryCustom {
    public List<PlaylistSong> findPlaylistBySongIdQD(Long songId);
    // song list 추가 전에 중복 체크
    List<PlaylistSong> findSavedSongListQD(Long playlistId, List<Long> songIdList);
}
