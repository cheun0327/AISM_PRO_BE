package com.upvote.aismpro.controller;


import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.security.SecurityUtil;
import com.upvote.aismpro.service.CreateService;
import com.upvote.aismpro.service.LikeService;
import com.upvote.aismpro.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CreateController {

    @Autowired
    private CreateService createService;
    @Autowired
    private SongService songService;

    @GetMapping("/create")
    public ResponseEntity<List<SongDTO>> getCreateList() {
        try {
            Long userId = SecurityUtil.getCurrentUserId();
            List<SongDTO> creates = songService.setLike2SongDTOList(createService.getCreates(userId), userId);
            return new ResponseEntity<>(creates, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
