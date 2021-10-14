package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.PlayList;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.repository.MyAlbumRepository;
import com.upvote.aismpro.repository.PlaylistRepository;
import com.upvote.aismpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MyMusicService implements MyMusicServiceInter{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyAlbumRepository myAlbumRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

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
//        List<Song> songs = userRepository.getById(userID).getMyAlbums();
//
//        for (Song song : songs) {
//            Map<String, Object> map = new HashMap<String, Object>() {{
//                put("userID", userID);
//                put("songID", song.getSongId());
//                put("songName", song.getSongName());
//                put("fileName", song.getFileName());
//                put("option", option);
//            }};
//            song_map.add(map);
//        }
        return song_map;
    }



//    public PlayList getPlaylistTest(String id) {
////        List<String>  ids = new ArrayList<>();
////        ids.add("1j0leaa8qcqzk5ha39v9");
////        return playlistRepository.findAllById(ids);
//        try {
//            PlayList playList = playlistRepository.getById(id);
//            return playList;
//        } catch(Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    // 내가 작곡한 음원 | 구매한 음원 가져오기
//    public List<Map<String, Object>> getMyComposeSongOrBuySong(String userID, String option) {
//        // List 형태 곡 정보 가져옴
//        List<Object[]> song_li = myAlbumRepository.findByUserIDAndOption(userID, option);
//
//        return convertDataType4Service(song_li, option);
//    }
//
//    @Override
//    // 좋아요 리스트 가져오기
//    public List<Map<String, Object>> getLikeSong(String userID, String option) {
//        // List 형태 곡 정보 가져옴
////        List<Object[]> song_li = myAlbumRepository.findLikeSongByUserID(userID);
////
////        return convertDataType4Service(song_li, option);
//        List<Map<String, Object>> song_map = new ArrayList<>();
//        List<Song> songs = myAlbumRepository.getById(userID).getSongs();
//
//        for (Song song : songs) {
//            Map<String, Object> map = new HashMap<String, Object>() {{
//                put("userID", userID);
//                put("songID", song.getSongId());
//                put("songName", song.getSongName());
//                put("fileName", song.getFileName());
//                put("option", option);
//            }};
//            song_map.add(map);
//        }
//        return song_map;
//    }
//
//    @Override
//    // 플레이 리스트 가져오기
//    public List<Map<String, Object>> getPlayList(String userID, String option) {
//        // List 형태 곡 정보 가져옴
//        List<Map<String, Object>> playlist_map = new ArrayList<>();
//
//        List<PlayList> playlist = myAlbumRepository.getById(userID).getPlaylists();
//
//        for (PlayList pl : playlist) {
//            Map<String, Object> map = new HashMap<String, Object>() {{
//                put("userID", userID);
//                put("playlistID", pl.getPlaylistId());
//                put("playlistName", pl.getName());
//                put("img", pl.getImg());
//                put("state", pl.getState());
//            }};
//            playlist_map.add(map);
//        }
//
//         return playlist_map;
//    }
//
//    // Repository -> Controller Data Type 변환
//    public List<Map<String, Object>> convertDataType4Service(List<Object[]> data, String option) {
//        // List 내 Map 형태로 곡 정보 저장
//        List<Map<String, Object>> song_map = new ArrayList<>();
//
//        // List 내 Map 형태로 곡 정보 변환
//        for (Object[] each_song_info : data) {
//            Map<String, Object> song_info = makeSongListToMap(each_song_info, option);
//
//            song_map.add(song_info);
//        }
//
//        return song_map;
//    }
//
//    // 곡 정보 List -> Map 형태 변환
//    public Map<String, Object> makeSongListToMap(Object[] song_info, String option) {
//        return option.equals("Play") ? convertPlayListType(song_info) : convertSongType(song_info, option);
//    }
//
//    // 내가 작곡한 음원 | 구매한 음원 | 좋아요 리스트 -> 데이터 타입 변환
//    public Map<String, Object> convertSongType(Object[] song_info, String option) {
//        String userID = (String) song_info[0];
//        String songID = (String) song_info[1];
//        String songName = (String) song_info[2];
//        String fileName = (String) song_info[3];
//
//        Map<String, Object> song_map = new HashMap<String, Object>() {{
//            put("userID", userID);
//            put("songID", songID);
//            put("songName", songName);
//            put("fileName", fileName);
//            put("option", option);
//        }};
//
//        return song_map;
//    }
//
//    // 플레이 리스트 데이터 타입 변환
//    public Map<String, Object> convertPlayListType(Object[] song_info) {
//        String userID = (String) song_info[0];
//        String playlistID = (String) song_info[1];
//        String playlistName = (String) song_info[2];
//        String img = (String) song_info[3];
//        String state = (String) song_info[4];
//
//        Map<String, Object> song_map = new HashMap<String, Object>() {{
//            put("userID", userID);
//            put("playlistID", playlistID);
//            put("playlistName", playlistName);
//            put("img", img);
//            put("state", state);
//        }};
//
//        return song_map;
//    }
}
