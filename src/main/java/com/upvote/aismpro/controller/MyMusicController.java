package com.upvote.aismpro.controller;


import com.upvote.aismpro.dto.PlaylistInfoDTO;
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
    public List<Like> getLike(@PathVariable("userId") String userId) {
        return myMusicService.getLikeList(userId);
    }

    @GetMapping("/create/{userId}")
    public List<Create> getCreate(@PathVariable("userId") String userId) {
        return myMusicService.getCreateList(userId);
    }

    @GetMapping("/buy/{userId}")
    public List<Buy> getBuy(@PathVariable("userId") String userId) {
        return myMusicService.getBuyList(userId);
    }

    @GetMapping("/sell/{userId}")
    public List<Sell> getSell(@PathVariable("userId") String userId) {
        return myMusicService.getSellList(userId);
    }

    @GetMapping("/playlist/{userId}")
    public List<PlayList> getPlaylist(@PathVariable("userId") String userId) {
        return myMusicService.getPlayList(userId);
    }
}