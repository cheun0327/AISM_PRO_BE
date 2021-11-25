package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.SimilarSrcDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.service.SongService;
import com.upvote.aismpro.service.SongServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class SongController {

    @Autowired
    private SongService songService;

    // Song Detail
    @GetMapping("/song/{songId}")
    public ResponseEntity<SongDTO> getSongDetail(@PathVariable("songId") Long songId) {
        try{
            SongDTO songDTO = songService.getSongDetail(songId);
            return new ResponseEntity<>(songDTO, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/song/similar/{songId}")
    public ResponseEntity<List<SongDTO>> getSimilarSong(@PathVariable("songId") Long songId) {
        return new ResponseEntity<>(songService.getSimilarSong(songId), HttpStatus.OK);
    }

    @GetMapping("/song/like/count/{songId}")
    public ResponseEntity<Integer> getSongLikeCnt(@PathVariable("songId") Long songId) {
        return new ResponseEntity<>(songService.getLikeCnt(songId), HttpStatus.OK);
    }
}
