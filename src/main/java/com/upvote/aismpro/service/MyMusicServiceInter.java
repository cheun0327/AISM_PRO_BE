package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.AlbumDTO;
import com.upvote.aismpro.entity.*;

import java.util.List;
import java.util.Map;

public interface MyMusicServiceInter{
    // 내가 작곡한 음원 | 구매한 음원 가져오기
    public List<AlbumDTO> getMyComposeSongOrBuySong(String userID, String option);

    // 좋아요 리스트 가져오기
    public List<Map<String, Object>> getLikeSong(String userID, String option);

    // 플레이 리스트 가져오기
    public List<Map<String, Object>> getPlayList(String userID, String option);

    // like list 가져오기
    public List<Like> getLikeList(String userId);

    // create list 가져오기
    public List<Create> getCreateList(String userId);

    // buy list 가져오기
    public List<Buy> getBuyList(String userId);

    // sell list 가젼오기
    public List<Sell> getSellList(String userId);
}
