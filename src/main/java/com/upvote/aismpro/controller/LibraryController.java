package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.service.LibraryServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class LibraryController {

    @Autowired
    private LibraryServiceInter libraryService;

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
}

