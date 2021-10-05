package com.upvote.aismpro.customrepository;

import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.entity.Song;

import java.util.List;

public interface SongRepositoryCustom {
    List<Song> findSongByIdListQD(List<String> songIdList);
}
