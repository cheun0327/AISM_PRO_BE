package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.service.SongServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
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
            return new ResponseEntity<SongDTO>(songDTO, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
