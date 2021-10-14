package com.upvote.aismpro.controller;

import com.upvote.aismpro.entity.PlayList;
import com.upvote.aismpro.service.MyAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("auth")
public class MyAlbumController {

    @Autowired
    private MyAlbumService myAlbumService;

    @GetMapping("/getSongByOption")
    public List<Map<String, Object>> getSongByOption(@RequestParam("userID") String userID, @RequestParam("option") String option) {
        switch (option) {
            case "Play": return myAlbumService.getPlayList(userID, option);
            case "Like": return myAlbumService.getLikeSong(userID, option);
            default: return myAlbumService.getMyComposeSongOrBuySong(userID, option);
        }
    }

    @GetMapping("/playlist/test/{id}")
    public void getPlaylist(@PathVariable("id") String id) {
        System.out.println(id);
        List<PlayList> pl = myAlbumService.getPlaylistTest(id);
        for (PlayList p : pl){
            System.out.println(p.getSong().size());
            System.out.println(p.getUser().getEmail());
        }
    }
}
