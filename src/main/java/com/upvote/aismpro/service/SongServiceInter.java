package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.MoodDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.entity.Song;

import java.util.List;
import java.util.Map;

public interface SongServiceInter {

    // 곡 정보 가져오기
    public SongDTO getSongDetail(String songId);
    // sond에 대한 like 개수 가져오기
    public Integer getLikeCnt(String songId);
    // 좋아요 생성
    public void creatLike(String userId, String songId);
    // 좋아요 삭제
    public void deleteLike(String likeId);
    // 비슷한 곡 가져오기
    public List<SongDTO> getSimilarSong(MoodDTO moodDTO);
    // 곡 저장
    public void createSong(Map<String, Object> param) throws Exception;
}
