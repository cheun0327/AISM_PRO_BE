package com.upvote.aismpro.customrepository;


import com.upvote.aismpro.dto.MoodDTO;
import com.upvote.aismpro.dto.NewPlaylistDTO;
import com.upvote.aismpro.dto.NewSongDTO;
import com.upvote.aismpro.dto.PlaylistDetailDTO;
import com.upvote.aismpro.entity.PlayList;

import java.util.List;

public interface PlaylistRepositoryCustom {
    public List<PlayList> findSimilarPlaylistQD(MoodDTO moodDTO);
    public List<PlayList> findNewSimilarPlaylistQD(NewSongDTO songDTO);
    public List<PlayList> findSimilarPlaylistPlaylistQD(PlaylistDetailDTO playlistDetailDTO);
}
