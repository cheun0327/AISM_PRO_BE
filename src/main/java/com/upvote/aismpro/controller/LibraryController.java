package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.service.LibraryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

@RestController
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

//    @Value("${page.size}")
//    private int pageSize;


    @GetMapping("/library/render")
    public ResponseEntity<Map<String, Object>> librarySearchOption() {
        Map<String, Object> map = libraryService.getSearchOptionDate();
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/library/search")
    public ResponseEntity<Map<String, Object>> librarySearch(@PageableDefault Pageable pageable,
                                                             @RequestBody LibrarySearchDTO librarySearchDTO) {
        System.out.println(pageable.getPageNumber());
        try {
            Map<String, Object> map = libraryService.getSearchResult(pageable, librarySearchDTO);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
