package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.AlbumDTO;
import com.upvote.aismpro.entity.*;
import com.upvote.aismpro.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class MyMusicService implements MyMusicServiceInter{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlbumRepository albumRepository;

    static final private ModelMapper modelMapper = new ModelMapper();

    @Override
    // 플레이 리스트 가져오기
    public List<Map<String, Object>> getPlayList(String userID, String option) {
        List<Map<String, Object>> playlist_map = new ArrayList<>();

        List<PlayList> playlist = userRepository.getById(userID).getPlaylists();

        for (PlayList pl : playlist) {
            Map<String, Object> map = new HashMap<String, Object>() {{
                put("userID", userID);
                put("playlistID", pl.getPlaylistId());
                put("playlistName", pl.getName());
                put("img", pl.getImg());
                put("state", pl.getState());
            }};
            playlist_map.add(map);
        }

         return playlist_map;
    }

    @Override
    // 좋아요 리스트 가져오기
    public List<Map<String, Object>> getLikeSong(String userID, String option) {
        List<Map<String, Object>> song_map = new ArrayList<>();

        return song_map;
    }

    @Override
    public List<AlbumDTO> getMyComposeSongOrBuySong(String userID, String option) {
        List<Album> albumEntity = albumRepository.findAll();

        return albumEntity.stream()
                .map(album -> modelMapper.map(album, AlbumDTO.class))
                .collect(Collectors.toList());

//        return albumRepository.findAll();

//        return albumRepository.findByUserID(userID);
    }
}