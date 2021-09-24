package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.MyAlbum;
import com.upvote.aismpro.repository.MyAlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.lwawt.macosx.CSystemTray;

import java.sql.SQLOutput;
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

        // List내 Map 형태로 곡 정보 저장
        List<Map<String, Object>> song_map = new ArrayList<>();

        // List내 Map 형태로 곡 정보 변환
        for (Object[] object : song_li) {
            Map<String, Object> song_info = new HashMap<String, Object>();

            String songID = (String) object[1];
            String songName = (String) object[2];
            String fileName = (String) object[3];

            song_info.put("userID", userID);
            song_info.put("songID", songID);
            song_info.put("songName", songName);
            song_info.put("fileName", fileName);
            song_info.put("option", option);

            song_map.add(song_info);
        }

        return song_map;
    }
}
