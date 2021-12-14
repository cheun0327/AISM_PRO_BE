package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.service.LibraryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public ResponseEntity<Map<String, Object>> librarySearch(@RequestBody LibrarySearchDTO librarySearchDTO) {

        try {
            Map<String, Object> map = libraryService.getSearchResult(librarySearchDTO);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/library/search/total/song")
    public ResponseEntity<List<SongDTO>> songTotalLibrarySearch(final Pageable pageable,
                                                               @RequestBody LibrarySearchDTO librarySearchDTO) {
        try {
            System.out.println(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
            System.out.println(pageable.getOffset() + " / " + pageable.getPageSize() + " / " + pageable.getPageNumber());

            List<SongDTO> songDTOList = libraryService.getTotalSongSearchResult(pageable, librarySearchDTO);

//            CollectionModel<SongDTO> result = CollectionModel.of(songDTOList);
//            Link link = linkTo(methodOn(LibraryController.class)
//                        .librarySearch(librarySearchDTO)).withRel("hi");
//            result.add(link);
//            System.out.println(result);

//            for (final EntityModel<SongDTO> s : result) {
//                System.out.println(s.getClass());
//                Link link = linkTo(methodOn(LibraryController.class)
//                        .librarySearch(librarySearchDTO)).withRel("hi");
//                System.out.println(link);
//                s.add(link);
//                System.out.println(s);
//            }
//            return new ResponseEntity<>(result, HttpStatus.OK);


            return new ResponseEntity<>(songDTOList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
