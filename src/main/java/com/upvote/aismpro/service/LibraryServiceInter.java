package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.entity.PlayList;

import java.util.List;
import java.util.Map;

public interface LibraryServiceInter {

    // 모든 플레이리스트 반환
    public List<PlaylistDTO> getAllPlaylists();

    // 라이브러리 검색 결과 반환
    public Map<String, Object> getSearch(LibrarySearchDTO librarySearchDto) throws Exception;

    // 라이브러리 옵션 데이터 반환
    public Map<String, List<String>> getRenderData();
}