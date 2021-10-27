package com.upvote.aismpro.controller;

import com.upvote.aismpro.service.PlaylistServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
