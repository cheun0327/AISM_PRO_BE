package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.MoodDTO;
import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.dto.PlaylistDetailDTO;
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

    @PostMapping("/playlist/like/{userId}/{playlistId}")
    private ResponseEntity<Object> createPlaylistLike(@PathVariable("userId") String userId,
                                              @PathVariable("playlistId") String playlistId) {
        playlistService.createPlaylistLike(userId, playlistId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/playlist/similar")
    public ResponseEntity<List<PlaylistDetailDTO>> getSimilarPlaylist(@RequestBody MoodDTO moodDTO) throws Exception {
        try {
            return new ResponseEntity<>(playlistService.getSimilarPlaylist(moodDTO), HttpStatus.OK);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
