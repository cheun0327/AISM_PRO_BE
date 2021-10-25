package com.upvote.aismpro.controller;

import com.upvote.aismpro.service.SongServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class SongController {

    @Autowired
    private SongServiceInter songService;

    @GetMapping("/song/{songId}")
    public Map<String, Object> getSongDetail(@PathVariable("songId") String songId) {
        return Collections.singletonMap("song", songService.getSongDetail(songId));
    }
}
