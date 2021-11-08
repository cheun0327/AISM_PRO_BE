package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.MoodDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.service.SongServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
public class SongController {

    @Autowired
    private SongServiceInter songService;

    @GetMapping("/song/{songId}")
    public ResponseEntity<SongDTO> getSongDetail(@PathVariable("songId") String songId) {
        try{
            SongDTO songDTO = songService.getSongDetail(songId);
            return new ResponseEntity<>(songDTO, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/song/similar")
    public ResponseEntity<List<SongDTO>> getSimilarSong(@RequestBody MoodDTO moodDTO) {
        return new ResponseEntity<>(songService.getSimilarSong(moodDTO), HttpStatus.OK);
    }

    @GetMapping("/like/songCount/{songId}")
    public ResponseEntity<Integer> getSongLikeCnt(@PathVariable("songId") String songId) {
        return new ResponseEntity<>(songService.getLikeCnt(songId), HttpStatus.OK);
    }

    @PostMapping("/like/{userId}/{songId}")
    public ResponseEntity<Object> createLike(@PathVariable("userId") String userId, @PathVariable("songId") String songId) {
        songService.creatLike(userId, songId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/like/{likeId}")
    public ResponseEntity<Object> deleteLike(@PathVariable("likeId") String likeId){
        songService.deleteLike(likeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
