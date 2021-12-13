package com.upvote.aismpro.customrepository;

import com.upvote.aismpro.dto.MyLibrarySearchDTO;
import com.upvote.aismpro.entity.Song;

import java.util.List;

public interface CreateRepositoryCustom {

    // MyLibraru 생성 음원 검색 결과
    public List<Song> findMyLibraryCreateSearchQD(Long userId, MyLibrarySearchDTO myLibrarySearchDTO);
}
