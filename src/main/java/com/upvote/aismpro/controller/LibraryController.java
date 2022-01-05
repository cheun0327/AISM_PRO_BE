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

    private final PagedResourcesAssembler<SongDTO> songDtoAssembler;
    private final PagedResourcesAssembler<PlaylistDTO> playlistDtoAssembler;
    private final PagedResourcesAssembler<ArtistDTO> artistDtoAssembler;

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
    public ResponseEntity<PagedModel<EntityModel<SongDTO>>> songTotalLibrarySearch(
            @PageableDefault(size=20,direction=Sort.Direction.DESC) final Pageable pageable,
            @RequestBody LibrarySearchDTO librarySearchDTO) {
        try {
            Page<SongDTO> songDTOList = libraryService.getTotalSongSearchResult(pageable, librarySearchDTO);

            PagedModel<EntityModel<SongDTO>> entityModels = songDtoAssembler.toModel(songDTOList,
                    model -> EntityModel.of(model, linkTo(methodOn(SongController.class).getSongDetail(model.getSongId())).withSelfRel()));

            return new ResponseEntity<>(entityModels, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/library/search/total/playlist")
    // TODO key값 변경 _embedded.playlistDToes -> _embedded.playlistDTOs
    public ResponseEntity<PagedModel<EntityModel<PlaylistDTO>>> playlistTotalLibrarySearch(
            @PageableDefault(size=15, direction=Sort.Direction.DESC) final Pageable pageable,
            @RequestBody LibrarySearchDTO librarySearchDTO) {
        try {
            Page<PlaylistDTO> playlistDTOList = libraryService.getTotalPlaylistSearchResult(pageable, librarySearchDTO);

            PagedModel<EntityModel<PlaylistDTO>> entityModels = playlistDtoAssembler.toModel(playlistDTOList,
                    model -> EntityModel.of(model, linkTo(methodOn(PlaylistController.class).getPlaylistDetail(model.getPlaylistId())).withSelfRel()));

            return new ResponseEntity<>(entityModels, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/library/search/total/artist")
    public ResponseEntity<PagedModel<EntityModel<ArtistDTO>>> artistTotalLibrarySearch(
            @PageableDefault(size=10, direction=Sort.Direction.DESC) final Pageable pageable,
            @RequestBody LibrarySearchDTO librarySearchDTO) {
        try {
            Page<ArtistDTO> artistDTOList = libraryService.getTotalArtistSearchResult(pageable, librarySearchDTO);

            PagedModel<EntityModel<ArtistDTO>> entityModels = artistDtoAssembler.toModel(artistDTOList,
                    model -> EntityModel.of(model, linkTo(methodOn(UserController.class).getArtistDetailInfo(model.getArtistId())).withSelfRel()));

            return new ResponseEntity<>(entityModels, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
