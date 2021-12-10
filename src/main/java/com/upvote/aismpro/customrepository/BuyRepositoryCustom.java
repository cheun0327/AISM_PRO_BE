package com.upvote.aismpro.customrepository;

import com.upvote.aismpro.dto.MyLibrarySearchDTO;
import com.upvote.aismpro.entity.Song;

import java.util.List;

public interface BuyRepositoryCustom {
    // MyLibraru 생성 음원 검색 결과
    public List<Song> findMyLibraryBuySearchQD(Long userId, MyLibrarySearchDTO myLibrarySearchDTO);
}
