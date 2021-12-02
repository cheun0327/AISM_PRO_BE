package com.upvote.aismpro.controller;


import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.security.SecurityUtil;
import com.upvote.aismpro.service.BuyService;
import com.upvote.aismpro.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BuyController {

    @Autowired
    private BuyService buyService;
    @Autowired
    private SongService songService;

    @GetMapping("/buy")
    public ResponseEntity<List<SongDTO>> getBuyList() {
        try{
            Long userId = SecurityUtil.getCurrentUserId();
            List<SongDTO> buys = songService.setLike2SongDTOList(buyService.getBuys(userId), userId);
            return new ResponseEntity<>(buys, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
