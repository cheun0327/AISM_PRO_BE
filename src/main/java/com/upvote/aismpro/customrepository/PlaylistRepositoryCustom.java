package com.upvote.aismpro.customrepository;

import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.entity.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlaylistRepositoryCustom {
    public List<Playlist> findAllPlaylistQD();
    public List<Playlist> findSimilarPlaylistQD(PlaylistDetailDTO playlistDetailDTO);
    public List<Playlist> findNewSimilarPlaylistQD(SongDTO songDTO);
    public List<Playlist> findNewSimilarPlaylistByTagsQD(SongTagDTO songTagDTO);
    // MyLibrary 플레이리스트 가져오기
    public List<Playlist> findMyLibraryAllPlaylistQD(Long userId);
    // MyLibrary 플레이리스트 검색 결과
    public List<Playlist> findMyLibraryPlaylistSearchQD(Long userId, MyLibrarySearchDTO myLibrarySearchDTO);
    // Library 플리이리스트 검색 결과
    public Page<Playlist> findLibraryPlaylistSearchQD(LibrarySearchDTO librarySearchDTO);
    public Page<Playlist> findLibraryTotalPlaylistSearchQD(Pageable pageable, LibrarySearchDTO librarySearchDTO);
    List<Playlist> findAllFetchPlaylistSongAndSongQD();
}
