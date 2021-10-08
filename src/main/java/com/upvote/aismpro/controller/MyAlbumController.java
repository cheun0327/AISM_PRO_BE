package com.upvote.aismpro.controller;

import com.upvote.aismpro.entity.MyAlbum;
import com.upvote.aismpro.service.MyAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("auth")
public class MyAlbumController {

    @Autowired
    private MyAlbumService myAlbumService;

    @GetMapping("/api/getSongByOption")
    public List<Map<String, Object>> getSongByOption(@RequestParam("userID") String userID, @RequestParam("option") String option) {
        switch (option) {
            case "Play": return myAlbumService.getPlayList(userID, option);
            case "Like": return myAlbumService.getLikeSong(userID, option);
            default: return myAlbumService.getMyComposeSongOrBuySong(userID, option);
        }
    }
}
