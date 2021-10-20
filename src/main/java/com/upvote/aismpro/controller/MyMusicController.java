package com.upvote.aismpro.controller;


import com.upvote.aismpro.entity.Album;
import com.upvote.aismpro.entity.Like;
import com.upvote.aismpro.entity.Create;
import com.upvote.aismpro.entity.Buy;
import com.upvote.aismpro.entity.Sell;
import com.upvote.aismpro.service.AlbumService;
import com.upvote.aismpro.dto.AlbumDTO;
import com.upvote.aismpro.service.MyMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
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


}
