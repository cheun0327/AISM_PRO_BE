package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.CreateDTO;
import com.upvote.aismpro.dto.LikeDTO;
import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.entity.*;
import com.upvote.aismpro.service.MyMusicService;
import com.upvote.aismpro.service.MyMusicServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
public class MyMusicController {

    @Autowired
    private MyMusicServiceInter myMusicService;

    @GetMapping("/like/{userId}")
    public ResponseEntity<List<LikeDTO>> getLike(@PathVariable("userId") String userId) {
        try{
            return new ResponseEntity<>(myMusicService.getLikeList(userId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/create/{userId}")
    public ResponseEntity<List<CreateDTO>> getCreate(@PathVariable("userId") String userId) {
        try{
            return new ResponseEntity<>(myMusicService.getCreateList(userId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buy/{userId}")
    public ResponseEntity<List<BuyDTO>> getBuy(@PathVariable("userId") String userId) {
        try{
            return new ResponseEntity<>(myMusicService.getBuyList(userId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sell/{userId}")
    public ResponseEntity<List<SellDTO>> getSell(@PathVariable("userId") String userId) {
        try{
            return new ResponseEntity<>(myMusicService.getSellList(userId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/playlist/{userId}")
    public ResponseEntity<List<PlaylistDTO>> getPlaylist(@PathVariable("userId") String userId) {
        try{
            return new ResponseEntity<>(myMusicService.getPlayList(userId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/playlist/detail/{playlistId}")
    public PlaylistDetailDTO getPlaylistDetail(@PathVariable("playlistId") String playlistId) {
        return myMusicService.getPlayListDetail(playlistId);
    }
}