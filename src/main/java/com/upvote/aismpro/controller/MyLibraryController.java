package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.MyLibraryDeleteDTO;
import com.upvote.aismpro.dto.MyLibrarySearchDTO;
import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
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

    @Autowired
    private PlaylistService playlistService;
    @Autowired
    private SongService songService;

    @GetMapping("/my-library/search")
    public ResponseEntity<Object> getMyLibrarySearch(@RequestBody MyLibrarySearchDTO myLibrarySearchDTO) {
        try {
            switch (myLibrarySearchDTO.getCategory()) {
                case "playlist" : {
                    return new ResponseEntity<>(playlistService.getMyLibrarySearchResult(myLibrarySearchDTO), HttpStatus.OK);
                }
                case "create" : {
                    return new ResponseEntity<>(createService.getMyLibrarySearchResult(myLibrarySearchDTO), HttpStatus.OK);
                }
                case "sell" : {
                    return new ResponseEntity<>(sellService.getMyLibrarySearchResult(myLibrarySearchDTO), HttpStatus.OK);
                }
                case "buy" : {
                    return new ResponseEntity<>(buyService.getMyLibrarySearchResult(myLibrarySearchDTO), HttpStatus.OK);
                }
                case "like" : {
                    return new ResponseEntity<>(likeService.getMyLibrarySearchResult(myLibrarySearchDTO), HttpStatus.OK);
                }
                default : {
                    throw new Exception("잘못된 카테고리");
                }
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/my-library")
    public ResponseEntity<Boolean> deleteMyLibraryData(@RequestBody MyLibraryDeleteDTO myLibraryDeleteDTO) {
        System.out.println(myLibraryDeleteDTO.getDeleteList());
        try {
            switch (myLibraryDeleteDTO.getCategory()) {
                case "create" : {
                    createService.deleteCreates(myLibraryDeleteDTO.getDeleteList());
                }
                case "sell" : {
                    sellService.deleteSells(myLibraryDeleteDTO.getDeleteList());
                }
                case "buy" : {
                    buyService.deleteBuys(myLibraryDeleteDTO.getDeleteList());
                }
                case "like" : {
                    likeService.deleteLikes(myLibraryDeleteDTO.getDeleteList());
                }
                case "playlist" : {
                    // TODO 플레이리스트 완전 삭제인지 좋아요 누른 플레이리스트 삭제인지
                    // TODO 제플린에는 나의 플레이리스트인듯한데 플레이리스트 좋아요는 어떻게 풀어나갈지
                }
            }

            return new ResponseEntity<>(true, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
