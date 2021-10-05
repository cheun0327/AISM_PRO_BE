package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.LibrarySearchDTO;

import java.util.Map;

public interface LibraryServiceInter {

    public Map<String, Object> getSearchResult(LibrarySearchDTO librarySearchDto);
}
