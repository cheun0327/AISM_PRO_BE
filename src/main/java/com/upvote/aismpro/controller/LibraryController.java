package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping("/library/render")
    public ResponseEntity<Map<String, List<String>>> librarySearchOption() {
        Map<String, List<String>> map = libraryService.getSearchOptionDate();
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/library/search")
    public ResponseEntity<Map<String, Object>> librarySearch(@RequestBody LibrarySearchDTO librarySearchDTO) {
        try {
            Map<String, Object> map = libraryService.getSearchResult(librarySearchDTO);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
