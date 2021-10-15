package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.AlbumDTO;
import com.upvote.aismpro.entity.Album;
import com.upvote.aismpro.entity.PlayList;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.service.MyMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class MyMusicController {

    @Autowired
    private MyMusicService myMusicService;

    @GetMapping("/getSongByOption")
    public List<Map<String, Object>> getSongByOption(@RequestParam("userID") String userID, @RequestParam("option") String option) {
        switch (option) {
            case "Play": return myMusicService.getPlayList(userID, option);
            case "Like": return myMusicService.getLikeSong(userID, option);
            // default: return myMusicService.getMyComposeSongOrBuySong(userID, option);
            default: return myMusicService.getLikeSong(userID, option);
        }
    }

    @GetMapping("/getSong")
    public List<AlbumDTO> getSong(@RequestParam("userID") String userID, @RequestParam("option") String option) {
        return myMusicService.getMyComposeSongOrBuySong(userID, option);
    }
}