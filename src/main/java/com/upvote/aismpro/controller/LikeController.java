package com.upvote.aismpro.controller;


import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.security.SecurityUtil;
import com.upvote.aismpro.service.LikeService;
import com.upvote.aismpro.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.RandomValuePropertySourceEnvironmentPostProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;
    @Autowired
    private SongService songService;

    @GetMapping("/like")
    public ResponseEntity<List<SongDTO>> getLikeList() {
        try {
            Long userId = SecurityUtil.getCurrentUserId();
            List<SongDTO> likes = songService.setLike2SongDTOList(likeService.getLikes(userId), userId);
            return new ResponseEntity<>(likes, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/like/{songId}")
    public ResponseEntity<Boolean> createLike(@PathVariable("songId") Long songId) {
        try {
            likeService.createLike(songId);
            return new ResponseEntity<>(true, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/like/{songId}")
    public ResponseEntity<Boolean> deleteLike(@PathVariable("songId") Long songId) {
        try {
            likeService.deleteLike(songId);
            return new ResponseEntity<>(true, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }
}
