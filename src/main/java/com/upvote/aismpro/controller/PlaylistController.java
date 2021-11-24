package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.dto.PlaylistDetailDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @GetMapping("/playlist/{userId}")
    public ResponseEntity<List<PlaylistDTO>> getMyPlaylist(@PathVariable("userId") Long userId) {
        try{
            return new ResponseEntity<>(playlistService.getPlayList(userId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/playlist/detail/{playlistId}")
    public ResponseEntity<PlaylistDetailDTO> getPlaylistDetail(@PathVariable("playlistId") Long playlistId) throws Exception {
        try{
            return new ResponseEntity<>(playlistService.getPlayListDetail(playlistId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 플레이리스트 디테일 페이지에서 비슷한 플레이 리스트 가져오기
    @PostMapping("/playlist/similar")
    public ResponseEntity<List<PlaylistDTO>> getSimilarPlaylist(@RequestBody PlaylistDetailDTO playlistDetailDTO) throws Exception {
        try {
            return new ResponseEntity<>(playlistService.getSimilarPlaylist(playlistDetailDTO), HttpStatus.OK);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 곡 디테일에서 비슷한 플레이 리스트 가져오기
    @PostMapping("/playlist/similar/song")
    public ResponseEntity<List<PlaylistDTO>> getNewSimilarPlaylist(@RequestBody SongDTO songDTO) throws Exception {
        try {
            return new ResponseEntity<>(playlistService.getSimilarPlaylistBySong(songDTO), HttpStatus.OK);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ver2
    @PostMapping("/playlist/saved/{songId}")
    public ResponseEntity<List<PlaylistDTO>> getSavedPlaylist(@PathVariable("songId") Long songId) {
        try {
            return new ResponseEntity<>(playlistService.getSavedPlaylistBySongId(songId), HttpStatus.OK);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 플레이리스트 좋아요 개수 가져오기
    @GetMapping("playlist/like/count/{playlistId}")
    public ResponseEntity<Integer> getPlaylistLikeCnt(@PathVariable("playlistId") Long playlistId) {
        try {
            return new ResponseEntity<>(playlistService.getPlaylistLikeCnt(playlistId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 플레이리스트 좋아요 저장하기
    @PostMapping("/playlist/like/{userId}/{playlistId}")
    public ResponseEntity<Object> createPlaylistLike(@PathVariable("userId") Long userId,
                                                     @PathVariable("playlistId") Long playlistId) {
        playlistService.createPlaylistLike(userId, playlistId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
