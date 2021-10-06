package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.dto.PlaylistInfoDTO;

import java.util.List;
import java.util.Map;

public interface LibraryServiceInter {

    public Map<String, Object> getSearchResult(LibrarySearchDTO librarySearchDto);

    // View Detail | Playlist
    public List<PlaylistInfoDTO> getPlaylistInfo(String category, String id);
}
