package com.upvote.aismpro.customrepository;

import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.dto.SimilarSrcDTO;
import com.upvote.aismpro.entity.Song;

import java.util.List;

public interface SongRepositoryCustom {
    public List<Song> findSimilarSongQD(Song song);
    public List<Song> findSongBySearchParamQD(LibrarySearchDTO librarySearchDTO);
}
