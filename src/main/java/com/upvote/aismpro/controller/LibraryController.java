package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.ArtistDTO;
import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/library/render")
    public ResponseEntity<Map<String, Object>> librarySearchOption() {
        Map<String, Object> map = libraryService.getSearchOptionDate();
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping(value = "/library/search", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> librarySearch(@RequestBody LibrarySearchDTO librarySearchDTO) {

        try {
            Map<String, Object> map = libraryService.getSearchResult(librarySearchDTO);

            Pageable songTotalDefaultPageable = PageRequest.of(0, 20);
            Pageable playlistTotalDefaultPageable = PageRequest.of(0, 15);
            Pageable artistTotalDefaultPageable = PageRequest.of(0, 10);

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
    public ResponseEntity<List<SongDTO>> songTotalLibrarySearch(final Pageable pageable,
                                                                @RequestBody LibrarySearchDTO librarySearchDTO) {
        try {
            System.out.println(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
            System.out.println(pageable.getOffset() + " / " + pageable.getPageSize() + " / " + pageable.getPageNumber());

            List<SongDTO> songDTOList = libraryService.getTotalSongSearchResult(pageable, librarySearchDTO);

            return new ResponseEntity<>(songDTOList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

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
