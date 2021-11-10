package com.upvote.aismpro.customrepository;


import com.upvote.aismpro.dto.MoodDTO;
import com.upvote.aismpro.entity.PlayList;

import java.util.List;

public interface PlaylistRepositoryCustom {
    public List<PlayList> findSimilarPlaylistQD(MoodDTO moodDTO);
}
