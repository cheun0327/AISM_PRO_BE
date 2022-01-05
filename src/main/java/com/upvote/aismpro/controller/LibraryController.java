package com.upvote.aismpro.controller;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.ArtistDTO;
import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.entity.Playlist;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.pagination.LinkResource;
import com.upvote.aismpro.pagination.PagedModelUtil;
import com.upvote.aismpro.repository.SongRepository;
import com.upvote.aismpro.service.LibraryService;

import com.upvote.aismpro.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class LibraryController {

    @Autowired
    private LibraryService libraryService;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private CustomModelMapper modelMapper;

    private final PagedResourcesAssembler<SongDTO> songDtoAssembler;

    @GetMapping("/library/render")
    public ResponseEntity<Map<String, Object>> librarySearchOption() {
        Map<String, Object> map = libraryService.getSearchOptionDate();
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping(value = "/library/search", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> librarySearch(@RequestBody LibrarySearchDTO librarySearchDTO) {

        try {
            Map<String, Object> map = libraryService.getSearchResult(librarySearchDTO);

            Pageable songTotalDefaultPageable = PageRequest.of(0,20);
            Pageable playlistTotalDefaultPageable = PageRequest.of(0,15);
            Pageable artistTotalDefaultPageable = PageRequest.of(0,10);

            EntityModel<Map<String, Object>> result = new EntityModel<>(map);
            result.add(
                    linkTo(methodOn(LibraryController.class)
                            .songTotalLibrarySearch(songTotalDefaultPageable, librarySearchDTO)).withRel("track"),
                    linkTo(methodOn(LibraryController.class)
                            .playlistTotalLibrarySearch(playlistTotalDefaultPageable, librarySearchDTO)).withRel("playlist"),
                    linkTo(methodOn(LibraryController.class)
                            .artistTotalLibrarySearch(artistTotalDefaultPageable, librarySearchDTO)).withRel("artist")
            );

            System.out.println(result);

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/library/search/total/song")
    public ResponseEntity<PagedModel<EntityModel<SongDTO>>> songTotalLibrarySearch(Pageable pageable,
                                                                                   @RequestBody LibrarySearchDTO librarySearchDTO) {
        try {
            System.out.println(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
            System.out.println(pageable.getOffset() + " / " + pageable.getPageSize() + " / " + pageable.getPageNumber());

            Page<SongDTO> songDTOList = libraryService.getTotalSongSearchResult(pageable, librarySearchDTO);

//            PagedModel<EntityModel<SongDTO>> entityModels = PagedModelUtil.getEntityModels(
//                    songDtoAssembler,
//                    songDTOList,
//                    linkTo(methodOn(SongController.class).getSongDetail(SongDTO::getSongId)),
//                    SongDTO::getSongId
//            );
            PagedModel<EntityModel<SongDTO>> entityModels = songDtoAssembler.toModel(songDTOList,
                    model -> EntityModel.of(model, linkTo(methodOn(SongController.class).getSongDetail(model.getSongId())).withSelfRel()));

            return new ResponseEntity<>(entityModels, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping("/library/search/total/song")
//    public ResponseEntity<List<SongDTO>> songTotalLibrarySearch(final Pageable pageable,
//                                                                @RequestBody LibrarySearchDTO librarySearchDTO) {
//        try {
//            System.out.println(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
//            System.out.println(pageable.getOffset() + " / " + pageable.getPageSize() + " / " + pageable.getPageNumber());
//
//            List<SongDTO> songDTOList = libraryService.getTotalSongSearchResult(pageable, librarySearchDTO);
//
//
//
////            CollectionModel<SongDTO> result = CollectionModel.of(songDTOList);
////            Link link = linkTo(methodOn(LibraryController.class)
////                        .librarySearch(librarySearchDTO)).withRel("hi");
////            result.add(link);
////            System.out.println(result);
//
////            for (final EntityModel<SongDTO> s : result) {
////                System.out.println(s.getClass());
////                Link link = linkTo(methodOn(LibraryController.class)
////                        .librarySearch(librarySearchDTO)).withRel("hi");
////                System.out.println(link);
////                s.add(link);
////                System.out.println(s);
////            }
////            return new ResponseEntity<>(result, HttpStatus.OK);
//
//            return new ResponseEntity<>(songDTOList, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @PostMapping("/library/search/total/playlist")
    public ResponseEntity<List<PlaylistDTO>> playlistTotalLibrarySearch(final Pageable pageable,
                                                                        @RequestBody LibrarySearchDTO librarySearchDTO) {
        try {
            List<PlaylistDTO> playlistDTOList = libraryService.getTotalPlaylistSearchResult(pageable, librarySearchDTO);
            return new ResponseEntity<>(playlistDTOList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/library/search/total/artist")
    public ResponseEntity<List<ArtistDTO>> artistTotalLibrarySearch(final Pageable pageable,
                                                                    @RequestBody LibrarySearchDTO librarySearchDTO) {
        try {
            List<ArtistDTO> playlistDTOList = libraryService.getTotalArtistSearchResult(pageable, librarySearchDTO);
            return new ResponseEntity<>(playlistDTOList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
