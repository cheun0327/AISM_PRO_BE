package com.upvote.aismpro.controller;


import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.entity.*;
import com.upvote.aismpro.service.MyMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class MyMusicController {

    @Autowired
    private MyMusicService myMusicService;

    @GetMapping("/like/{userId}")
    public List<LikeDTO> getLike(@PathVariable("userId") String userId) {
        return myMusicService.getLikeList(userId);
    }

    @GetMapping("/create/{userId}")
    public List<CreateDTO> getCreate(@PathVariable("userId") String userId) {
        return myMusicService.getCreateList(userId);
    }

    @GetMapping("/buy/{userId}")
    public List<BuyDTO> getBuy(@PathVariable("userId") String userId) {
        return myMusicService.getBuyList(userId);
    }

    @GetMapping("/sell/{userId}")
    public List<SellDTO> getSell(@PathVariable("userId") String userId) {
        return myMusicService.getSellList(userId);
    }

    @GetMapping("/playlist/{userId}")
    public List<PlayList> getPlaylist(@PathVariable("userId") String userId) {
        return myMusicService.getPlayList(userId);
    }

    @GetMapping("/playlist/detail/{playlistId}")
    public List<PlayList> getPlaylistDetail(@PathVariable("playlistId") String playlistId) {
        return myMusicService.getPlayList("");
    }
}