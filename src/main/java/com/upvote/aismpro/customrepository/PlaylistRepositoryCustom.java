package com.upvote.aismpro.customrepository;

import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.entity.Playlist;

import java.util.List;

public interface PlaylistRepositoryCustom {
    public List<Playlist> findSimilarPlaylistQD(PlaylistDetailDTO playlistDetailDTO);
    public List<Playlist> findNewSimilarPlaylistQD(SongDTO songDTO);
    public List<Playlist> findNewSimilarPlaylistByTagsQD(SongTagDTO songTagDTO);
    // MyLibrary 플레이리스트 검색 결과
    public List<Playlist> findMyLibraryPlaylistSearchQD(Long userId, MyLibrarySearchDTO myLibrarySearchDTO);
}
