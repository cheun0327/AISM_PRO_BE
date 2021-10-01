package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.LibrarySearchDTO;

import java.util.List;

public interface LibraryServiceInter {

    public List<String> getSearchResult(LibrarySearchDTO librarySearchDto);
}
