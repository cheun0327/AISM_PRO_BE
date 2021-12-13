package com.upvote.aismpro.customrepository;

import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.dto.SimilarSrcDTO;
import com.upvote.aismpro.dto.SongTagDTO;
import com.upvote.aismpro.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SongRepositoryCustom {
    public List<Song> findSimilarSongQD(Song song);
    public List<Song> findSimilarSongByTagsQD(SongTagDTO songTagDTO);
    public Page<Song> findSongBySearchParamQD(LibrarySearchDTO librarySearchDTO);
}
