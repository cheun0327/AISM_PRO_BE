package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.MyLibraryDeleteDTO;
import com.upvote.aismpro.dto.MyLibrarySearchDTO;
import com.upvote.aismpro.service.BuyService;
import com.upvote.aismpro.service.CreateService;
import com.upvote.aismpro.service.LikeService;
import com.upvote.aismpro.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

//    @DeleteMapping("/my-library")
//    public ResponseEntity<Boolean> deleteMyLibraryData(@RequestBody MyLibraryDeleteDTO myLibraryDeleteDTO) {
//        try {
//            switch (myLibraryDeleteDTO.getCategory()) {
//                case "create" : {
//                    createService.deleteCreates(myLibraryDeleteDTO.getDeleteList());
//                }
//                case "sell" : {
//                    sellService.deleteSells(myLibraryDeleteDTO.getDeleteList());
//                }
//                case "buy" : {
//                    buyService.deleteBuys(myLibraryDeleteDTO.getDeleteList());
//                }
//                case "like" : {
//                    likeService.deleteLikes(myLibraryDeleteDTO.getDeleteList());
//                }
//            }
//
//            return new ResponseEntity<>(true, HttpStatus.OK);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
}
