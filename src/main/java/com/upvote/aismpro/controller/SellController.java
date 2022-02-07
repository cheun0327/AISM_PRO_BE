package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.security.SecurityUtil;
import com.upvote.aismpro.service.SellService;
import com.upvote.aismpro.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SellController {

    @Autowired
    private SellService sellService;
    @Autowired
    private SongService songService;

    @GetMapping("/sell")
    public ResponseEntity<List<SongDTO>> getSellList() {
        try {
            Long userId = SecurityUtil.getCurrentUserId();
            List<SongDTO> likes = songService.setLike2SongDTOList(sellService.getSells(userId), userId);
            return new ResponseEntity<>(likes, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sell/{songId}")
    public ResponseEntity<Boolean> createSell(@PathVariable Long songId) {
        try {
            sellService.createSells(songId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
