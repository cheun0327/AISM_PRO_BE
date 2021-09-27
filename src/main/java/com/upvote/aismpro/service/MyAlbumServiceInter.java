package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.MyAlbum;

import java.util.List;
import java.util.Map;

public interface MyAlbumServiceInter{

    // 내가 작곡한 음원 | 구매한 음원 가져오기
    public List<Map<String, Object>> getMyComposeSongOrBuySong(String userID, String option);

    // 좋아요 리스트 가져오기
    public List<Map<String, Object>> getLikeSong(String userID, String option);

    // 플레이 리스트 가져오기
    public List<Map<String, Object>> getPlayList(String userID, String option);
}
