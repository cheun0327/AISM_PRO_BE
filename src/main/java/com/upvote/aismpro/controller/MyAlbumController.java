package com.upvote.aismpro.controller;

import com.upvote.aismpro.entity.MyAlbum;
import com.upvote.aismpro.service.MyAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class MyAlbumController {

    @Autowired
    private MyAlbumService myAlbumService;

    @GetMapping("/getAllSong")
    public List<Map<String, Object>> getAllSong(@RequestParam("userID") String userID, @RequestParam("option") String option) {
        return myAlbumService.getAllSong(userID, option);
    }
}
