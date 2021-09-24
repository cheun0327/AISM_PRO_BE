package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.MyAlbum;
import com.upvote.aismpro.repository.MyAlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyAlbumService implements MyAlbumServiceInter{

    @Autowired
    private MyAlbumRepository myAlbumRepository;

    @Override
    public List<Map<String, Object>> getAllSong(String userID, String option) {
        // List 형태 곡 정보 가져옴
        List<Object[]> song_li = myAlbumRepository.findByUserID(userID, option);

        // List 내 Map 형태로 곡 정보 저장
        List<Map<String, Object>> song_map = new ArrayList<>();

        // List 내 Map 형태로 곡 정보 변환
        for (Object[] each_song_info : song_li) {
            Map<String, Object> song_info = makeSongListToMap(each_song_info);

            song_map.add(song_info);
        }

        return song_map;
    }

    // 곡 정보 List -> Map 형태 변환
    public Map<String, Object> makeSongListToMap(Object[] song_info) {
        String userID = (String) song_info[0];
        String songID = (String) song_info[1];
        String songName = (String) song_info[2];
        String fileName = (String) song_info[3];
        String option = (String) song_info[4];

        Map<String, Object> song_map = new HashMap<String, Object>() {{
            put("userID", userID);
            put("songID", songID);
            put("songName", songName);
            put("fileName", fileName);
            put("option", option);
        }};

        return song_map;
    }
}
