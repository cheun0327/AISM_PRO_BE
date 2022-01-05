package com.upvote.aismpro.customrepository;

import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.dto.SimilarSrcDTO;
import com.upvote.aismpro.dto.SongTagDTO;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SongRepositoryCustom {
    public List<Song> findSimilarSongQD(Song song);
    public List<Song> findSimilarSongByTagsQD(SongTagDTO songTagDTO);
    public Page<Song> findSongBySearchParamQD(LibrarySearchDTO librarySearchDTO);
    public Page<Song> findLibraryTotalSongSearchQD(Pageable pageable, LibrarySearchDTO librarySearchDTO);
    public List<User> findLibraryArtistSearchQD(String search);
    public Page<User> findLibraryTotalArtistSearchQD(Pageable pageable, String search);
}
