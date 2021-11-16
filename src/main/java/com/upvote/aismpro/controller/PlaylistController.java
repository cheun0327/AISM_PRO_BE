package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.entity.PlayListSong;
import com.upvote.aismpro.service.MyMusicServiceInter;
import com.upvote.aismpro.service.PlaylistServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class PlaylistController {

    @Autowired
    private PlaylistServiceInter playlistService;
    @Autowired
    private MyMusicServiceInter myMusicService;

    @PostMapping("/playlist/like/{userId}/{playlistId}")
    public ResponseEntity<Object> createPlaylistLike(@PathVariable("userId") String userId,
                                              @PathVariable("playlistId") String playlistId) {
        playlistService.createPlaylistLike(userId, playlistId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/playlist/{userId}")
    public ResponseEntity<List<NewPlaylistDTO>> getMyPlaylist(@PathVariable("userId") String userId) {
        try{
            return new ResponseEntity<>(myMusicService.getNewPlayList(userId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/playlist/detail/{playlistId}")
    public ResponseEntity<PlaylistDetailDTO> getPlaylistDetail(@PathVariable("playlistId") String playlistId) throws Exception {
        try{
            return new ResponseEntity<>(myMusicService.getPlayListDetail(playlistId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/playlist/similar")
//    public ResponseEntity<List<PlaylistDetailDTO>> getSimilarPlaylist(@RequestBody MoodDTO moodDTO) throws Exception {
//        try {
//            return new ResponseEntity<>(playlistService.getSimilarPlaylist(moodDTO), HttpStatus.OK);
//        } catch(NoSuchElementException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } catch(Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    // 플레이리스트 디테일 페이지에서 비슷한 플레이 리스트 가져오기
    @PostMapping("/playlist/similar")
    public ResponseEntity<List<NewPlaylistDTO>> getSimilarPlaylist(@RequestBody PlaylistDetailDTO playlistDetailDTO) throws Exception {
        try {
            return new ResponseEntity<>(playlistService.getNewSimilarPlaylistPlaylist(playlistDetailDTO), HttpStatus.OK);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 곡 디테일에서 비슷한 플레이 리스트 가져오기
    @PostMapping("/playlist/similar/song")
    public ResponseEntity<List<NewPlaylistDTO>> getNewSimilarPlaylist(@RequestBody NewSongDTO songDTO) throws Exception {
        try {
            return new ResponseEntity<>(playlistService.getNewSimilarPlaylist(songDTO), HttpStatus.OK);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ver2
    @PostMapping("/playlist/saved/{songId}")
    public ResponseEntity<List<NewPlaylistDTO>> getSavedPlaylist(@PathVariable("songId") String songId) {
        try {
            return new ResponseEntity<>(playlistService.getSavedPlaylistBySongID(songId), HttpStatus.OK);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("like/playlistCount/{playlistID}")
    public ResponseEntity<Integer> getPlaylistLikeCnt(@PathVariable("playlistID") String playlistID) {
        try {
            return new ResponseEntity<>(playlistService.getPlaylistLikeCnt(playlistID), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
