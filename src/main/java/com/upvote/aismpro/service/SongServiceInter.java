package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.SongDTO;

public interface SongServiceInter {

    // 곡 정보 가져오기
    public SongDTO getSongDetail(String songId);
}
