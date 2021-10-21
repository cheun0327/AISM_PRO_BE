package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.service.LibraryServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class LibraryController {

    @Autowired
    private LibraryServiceInter libraryService;

    // 라이브러리 검색
    @PostMapping("/library/search")
    public Map<String, Object> librarySearch(@RequestBody LibrarySearchDTO libSearchDto) {
        return libraryService.getSearch(libSearchDto);
    }

}

