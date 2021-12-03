package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.MyLibrarySearchDTO;
import com.upvote.aismpro.service.BuyService;
import com.upvote.aismpro.service.CreateService;
import com.upvote.aismpro.service.LikeService;
import com.upvote.aismpro.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyLibraryController {

    @Autowired
    private CreateService createService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private BuyService buyService;
    @Autowired
    private SellService sellService;
//
//    @GetMapping("/my-library/search")
//    public ResponseEntity<List<Object>> getMyLibrarySearch(@RequestBody MyLibrarySearchDTO myLibrarySearchDTO) {
//        try {
//            switch (myLibrarySearchDTO.getCategory()) {
//                case "playlist" :
//
//            }
//        } catch (Exception e) {
//
//        }
//    }
}
