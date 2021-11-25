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

    // 유저의 플레이리스트 리스트 가져오기
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

    // 특정 플레이리스트 디테일 정보 가져오기
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
    // like 어떻게 표시해 줄지 생각해보기
    @GetMapping("/playlist/similar/{playlistId}")
    public ResponseEntity<List<PlaylistDTO>> getSimilarPlaylist(@PathVariable("playlistId") Long playlistId) throws Exception {
        try {
            return new ResponseEntity<>(playlistService.getSimilarPlaylist(playlistId), HttpStatus.OK);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 곡 디테일에서 비슷한 플레이 리스트 가져오기
    @GetMapping("/playlist/similar/song/{songId}")
    public ResponseEntity<List<PlaylistDTO>> getNewSimilarPlaylist(@PathVariable("songId") Long songId) throws Exception {
        try {
            return new ResponseEntity<>(playlistService.getSimilarPlaylistBySong(songId), HttpStatus.OK);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 해당 음원이 저장된 플레이리스트 찾기
    @GetMapping("/playlist/saved/{songId}")
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
    @GetMapping("/playlist/like/count/{playlistId}")
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
