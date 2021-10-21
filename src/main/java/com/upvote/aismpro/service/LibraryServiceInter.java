package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.entity.PlayList;

import java.util.List;
import java.util.Map;

public interface LibraryServiceInter {

    public List<PlayList> getPlaylistDto();

    // 라이브러리 검색 결과 반환
    public Map<String, Object> getSearch(LibrarySearchDTO librarySearchDto);
}