package com.upvote.aismpro.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.dto.SongSaveDTO;
import com.upvote.aismpro.dto.SongTagDTO;
import com.upvote.aismpro.exception.ApiException;
import com.upvote.aismpro.security.SecurityUtil;
import com.upvote.aismpro.service.PlaylistService;
import com.upvote.aismpro.service.SongService;
import com.upvote.aismpro.vo.SongSaveVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
public class SongController {

    private final SongService songService;
    private final PlaylistService playlistService;

    ////////////////////////   song create => MEMBER(credit>0)   ////////////////////////
    // song 생성 => 생성 가능 권한 확인
    @PostMapping("/song")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Song created successfully"),
            @ApiResponse(code = 400, message = "Bad Request(+File Size Exceeded)"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    public ResponseEntity<Long> createSong(@ModelAttribute SongSaveVO songVO) {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // JSON String -> DTO
        SongSaveDTO songSaveReqDTO;
        try {
            songSaveReqDTO = mapper.readValue(songVO.getVal(), SongSaveDTO.class);
        } catch (IOException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "잘못된 요청입니다. 입력값을 확인 해 주세요.");
        }

        // song 기본 정보 저장
        Long savedSongId = songService.saveSong(songSaveReqDTO, songVO.getImg());

        return new ResponseEntity<>(savedSongId, HttpStatus.CREATED);
    }

    // song 저장 => 저장 권한 확인 후 저장


    ////////////////////////   song read => GUEST && MEMBER   ////////////////////////
    // Song Detail With Like
    @GetMapping("/song/{songId}")
    public ResponseEntity<SongDTO> getSongDetail(@PathVariable("songId") Long songId) {
        try {
            Long userId = SecurityUtil.getCurrentUserId();

            SongDTO songDTO = songService.getSongDetail(songId);
            if (userId != -1) {
                songDTO = songService.setLike2SongDTO(songDTO, userId);
            }

            return new ResponseEntity<>(songDTO, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Song Similar With Like
    @GetMapping("/song/similar/{songId}")
    public ResponseEntity<List<SongDTO>> getSimilarSong(@PathVariable("songId") Long songId) {
        try {
            Long userId = SecurityUtil.getCurrentUserId();

            List<SongDTO> songDTOList = songService.getSimilarSong(songId);
            if (userId != -1) {
                songDTOList = songService.setLike2SongDTOList(songDTOList, userId);
            }

            return new ResponseEntity<>(songDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 작곡하기 step2 비슷한 음원, 플레이리스트 가져오기
    @PostMapping("/song/similar/tags")
    public ResponseEntity<Map<String, Object>> getSimilarSongByTags(@RequestBody SongTagDTO songTagDTO) {
        try {
            System.out.println(songTagDTO);
            Map<String, Object> map = new HashMap<>();
            Long userId = SecurityUtil.getCurrentUserId();

            List<SongDTO> songDTOList = songService.getSimilarSongByTags(songTagDTO);
            List<PlaylistDTO> playlistDTOList = playlistService.getSimilarPlaylistByTags(songTagDTO);

//            if (userId != -1) {
//                songDTOList = songService.setLike2SongDTOList(songDTOList, userId);
//                playlistDTOList = playlistService.setLike2PlaylistDTOList(playlistDTOList, userId);
//            }

            map.put("song", songDTOList);
            map.put("playlist", playlistDTOList);

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    ////////////////////////   song update   ////////////////////////


    ////////////////////////   song delete   ////////////////////////


    ////////////////////////   song utils   ////////////////////////
    // song의 like 개수 cnt
    @GetMapping("/song/like/count/{songId}")
    public ResponseEntity<Integer> getSongLikeCnt(@PathVariable("songId") Long songId) {
        return new ResponseEntity<>(songService.getLikeCnt(songId), HttpStatus.OK);
    }

    @ApiOperation(value = "해당 사용자가 작곡한 곡 리스트 불러오기")
    @GetMapping("/create")
    public ResponseEntity<List<SongDTO>> getSongList() {
        Long userId = SecurityUtil.getCurrentUserId();
        List<SongDTO> dtoList = songService.setLike2SongDTOList(songService.getSongListByUserId(userId), userId);
        return ResponseEntity.ok(dtoList);
    }

    @ApiOperation(value = "해당 사용자가 작곡한 곡 리스트 불러오기")
    @GetMapping("/create/{userID}")
    public ResponseEntity<List<SongDTO>> getCreateListByUserID(@PathVariable("userID") Long userId) {
        List<SongDTO> dtoList = songService.setLike2SongDTOList(songService.getSongListByUserId(userId), userId);
        return ResponseEntity.ok(dtoList);

    }
}
