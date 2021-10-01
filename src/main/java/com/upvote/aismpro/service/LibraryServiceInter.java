package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.entity.Song;

import java.util.List;

public interface LibraryServiceInter {

    public List<Song> getSearchResult(LibrarySearchDTO librarySearchDto);
}
