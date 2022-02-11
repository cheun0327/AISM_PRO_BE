package com.upvote.aismpro.customrepository;

import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.dto.SongTagDTO;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SongRepositoryCustom {
    public List<Song> findSimilarSongQD(
            Long songId,
            String genre, String category, String subCategory,
            String keyword, String fx
    );

    public List<Song> findSimilarSongByTagsQD(SongTagDTO songTagDTO);

    List<Song> findSongBySearchParamQD(
            String searchKeyword, String type, String sort,
            List<String> genre, List<String> inst, List<String> mood
    );

    List<Song> findLibraryTotalSongSearchQD(Pageable pageable, LibrarySearchDTO librarySearchDTO);

    public List<User> findLibraryArtistSearchQD(String search);

    public List<User> findLibraryTotalArtistSearchQD(Pageable pageable, String search);

    List<Song> findAllByIdFetchUserQD(List<Long> songIdList);

    List<Song> findAllByUserIdFetchUserQD(Long userId);

    List<Song> searchSongListQD(Long userId, String searchStr);

    boolean isEnoughAddToPlaylistQD(Long userId);

    List<Song> findSongListByUserIdLimit3QD(Long userId);

    Song findByIdFetchKeywordPathQD(Long songId);
}
