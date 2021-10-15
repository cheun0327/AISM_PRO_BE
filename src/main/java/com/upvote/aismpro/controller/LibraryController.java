package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.dto.PlaylistInfoDTO;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.service.LibraryService;
import com.upvote.aismpro.service.LibraryServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class LibraryController {

    @Autowired
    private LibraryServiceInter libraryService;

    // 장르가 중복 선택 가능한가
    // 장르마다 mood1 카테고리 다를 수 있지않나
    // get으로 gnere=pop,country&mood1=감미로운,강렬한,즐거운&mood2=유쾌한,어쩌구,저쩌구 이렇게 오는게 맞는거같다
    @PostMapping("/library/search")
    public Map<String, Object> librarySearch(@RequestBody LibrarySearchDTO libSearchDto) {
        System.out.println("libcontroller");
        libSearchDto.print();
        return libraryService.getSearchResult(libSearchDto);
    }

    //@GetMapping("/getPlaylistInfo")
    @GetMapping("/playlist")
    public List<PlaylistInfoDTO> viewDetail(@RequestParam("category") String category, @RequestParam("id") String id) {
        return libraryService.getPlaylistInfo(category, id);
    }

}