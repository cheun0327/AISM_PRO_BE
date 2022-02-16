package com.upvote.aismpro.customrepository;

import com.upvote.aismpro.dto.MyLibrarySearchDTO;
import com.upvote.aismpro.entity.Like;
import com.upvote.aismpro.entity.Song;

import java.util.List;

public interface LikeRepositoryCustom {
    public List<Song> findMyLibraryLikeSearchQD(Long userId, MyLibrarySearchDTO myLibrarySearchDTO);

    List<Like> findByUserIdAndSongIdIn(Long userId, List<Long> songIdList);
}
