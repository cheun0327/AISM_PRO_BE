package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.dto.NewLibrarySearchDTO;
import com.upvote.aismpro.entity.Like;
import com.upvote.aismpro.entity.NewSong;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.repository.LikeRepository;
import com.upvote.aismpro.repository.NewSongRepository;
import com.upvote.aismpro.service.LibraryServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
public class LibraryController {

    @Autowired
    private LibraryServiceInter libraryService;
    @Autowired
    private NewSongRepository newSongRepository;
    @Autowired
    private LikeRepository likeRepository;

    // 라이브러리 검색
    @PostMapping("/library/search")
    public ResponseEntity<Map<String, Object>> librarySearch(@RequestBody LibrarySearchDTO libSearchDto) {
        try{
            return new ResponseEntity<>(libraryService.getSearch(libSearchDto), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/library/new/search")
    public ResponseEntity<Map<String, Object>> libraryNewSearch(@RequestBody NewLibrarySearchDTO libSearchDto) {
        try{
            return new ResponseEntity<>(libraryService.getNewSearch(libSearchDto), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/wav")
    public String wavTest()  {
        try {
//            File file = new File(".");
//            String rootPath = file.getAbsolutePath();
//            System.out.println("현재 프로젝트의 경로 : "+rootPath );
            AudioFileFormat aff = AudioSystem.getAudioFileFormat(new File("./song/sos.wav"));
            AudioFormat af = aff.getFormat();

            System.out.println(aff.toString());
            System.out.println(af.toString());
            System.out.println(aff.getFrameLength() / af.getFrameRate());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @GetMapping("/library/render")
    public ResponseEntity<Map<String, List<String>>> libraryRender() {
        Map<String, List<String>> map = libraryService.getRenderData();
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/jpatest")
    public ResponseEntity<List<Like>> test() {
        List<Like> likes = likeRepository.findAllByUser("5aab8f4c-e752-485d-98b6-1cb2bfc5ee74");
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }

//    @PostMapping("/new/library/search")
//    public ResponseEntity<Map<String, Object>> newLibrarySearch(@ResponseBody NewLibrarySearchDTO newLibrarySearchDTO) {
//
//    }
}

