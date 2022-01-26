package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.MyLibraryDeleteDTO;
import com.upvote.aismpro.dto.MyLibrarySearchDTO;
import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.dto.PlaylistDetailDTO;
import com.upvote.aismpro.security.SecurityUtil;
import com.upvote.aismpro.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MyLibraryController {

    private final LikeService likeService;
    private final BuyService buyService;
    private final SellService sellService;
    private final PlaylistService playlistService;
    private final SongService songService;

    @GetMapping("/my-library/playlist")
    public ResponseEntity<List<PlaylistDetailDTO>> getPlaylistByUserID() {
        try {
            Long userId = SecurityUtil.getCurrentUserId();
            if (userId < 0) throw new Exception();
            return new ResponseEntity<>(playlistService.getUserPlaylist(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/my-library/search")
    public ResponseEntity<Object> getMyLibrarySearch(@RequestParam("category") String category, @RequestParam("sort") String sort, @RequestParam("search") String search) {
        MyLibrarySearchDTO myLibrarySearchDTO = new MyLibrarySearchDTO(search, sort, category);

        try {
            switch (myLibrarySearchDTO.getCategory()) {
                case "playlist": {
                    return new ResponseEntity<>(playlistService.getMyLibrarySearchResult(myLibrarySearchDTO), HttpStatus.OK);
                }
                case "create": {
                    return new ResponseEntity<>(songService.searchSongList(myLibrarySearchDTO), HttpStatus.OK);
                }
                case "sell": {
                    return new ResponseEntity<>(sellService.getMyLibrarySearchResult(myLibrarySearchDTO), HttpStatus.OK);
                }
                case "buy": {
                    return new ResponseEntity<>(buyService.getMyLibrarySearchResult(myLibrarySearchDTO), HttpStatus.OK);
                }
                case "like": {
                    return new ResponseEntity<>(likeService.getMyLibrarySearchResult(myLibrarySearchDTO), HttpStatus.OK);
                }
                default: {
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
                case "create": {
                    songService.deleteSongs(myLibraryDeleteDTO.getDeleteList());
                    break;
                }
                case "sell": {
                    sellService.deleteSells(myLibraryDeleteDTO.getDeleteList());
                    break;
                }
                case "buy": {
                    buyService.deleteBuys(myLibraryDeleteDTO.getDeleteList());
                    break;
                }
                case "like": {
                    likeService.deleteLikes(myLibraryDeleteDTO.getDeleteList());
                    break;
                }
                case "playlist": {
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
