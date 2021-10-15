package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.AlbumDTO;
import com.upvote.aismpro.entity.*;
import com.upvote.aismpro.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

@Service
public class MyMusicService implements MyMusicServiceInter{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlbumRepository albumRepository;

    private ModelMapper modelMapper = new ModelMapper();

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
        List<AlbumDTO> albums = new ArrayList<AlbumDTO>();

        for (Album album : albumEntity) {
            AlbumDTO albumDTO = modelMapper.map(album, AlbumDTO.class);
            albums.add(albumDTO);
        }

        return albums;
    }
}
