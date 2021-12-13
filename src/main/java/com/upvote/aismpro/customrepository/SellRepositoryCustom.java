package com.upvote.aismpro.customrepository;

import com.upvote.aismpro.dto.MyLibrarySearchDTO;
import com.upvote.aismpro.entity.Song;

import java.util.List;

public interface SellRepositoryCustom {
    public List<Song> findMyLibrarySellSearchQD(Long userId, MyLibrarySearchDTO myLibrarySearchDTO);
}
