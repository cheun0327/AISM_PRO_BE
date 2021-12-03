package com.upvote.aismpro.customrepository;

import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.dto.PlaylistDetailDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.dto.SongTagDTO;
import com.upvote.aismpro.entity.Playlist;

import java.util.List;

public interface PlaylistRepositoryCustom {
    public List<Playlist> findSimilarPlaylistQD(PlaylistDetailDTO playlistDetailDTO);
    public List<Playlist> findNewSimilarPlaylistQD(SongDTO songDTO);
    public List<Playlist> findNewSimilarPlaylistByTagsQD(SongTagDTO songTagDTO);
}
