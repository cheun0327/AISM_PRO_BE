package com.upvote.aismpro.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.security.SecurityUtil;
import com.upvote.aismpro.service.PlaylistService;
import com.upvote.aismpro.vo.PlaylistSaveVO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
public class PlaylistController {

    private final PlaylistService playlistService;

    ////////////////////////   playlist create   ////////////////////////
    // 플리이리스트 껍데기 만들기
    @PostMapping("/playlist")
    public ResponseEntity<Long> createPlaylist(@ModelAttribute PlaylistSaveVO playlistVO) {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            PlaylistSaveDTO playlistSaveDTO = mapper.readValue(playlistVO.getVal(), PlaylistSaveDTO.class);
            System.out.println(playlistSaveDTO);
            Long playlistId = playlistService.createPlaylist(playlistSaveDTO, playlistVO.getImg());
            return ResponseEntity.ok(playlistId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "유저가 작곡한 곡 3개 불러오기", notes = "플레이리스트에 추가할 곡 3개 리스트로 반환")
    @GetMapping("/playlist/song/select")
    public ResponseEntity<SongListForAddToPlaylistDTO> getSongListAddToPlaylist() {
        return playlistService.getSongListAddToPlaylist();
    }

    @ApiOperation(value = "플레이리스트에 곡 추가")
    @PostMapping("/playlist/song")
    public ResponseEntity<SaveSongListResDTO> addSongList(@RequestBody SaveSongListReqDTO dto) {
        return playlistService.addSongList(dto);
    }

    ////////////////////////   playlist read   ////////////////////////
    // 유저의 플레이리스트 리스트 가져오기
    @GetMapping("/playlist")
    public ResponseEntity<List<PlaylistDTO>> getMyPlaylist() {
        try {
            Long userId = SecurityUtil.getCurrentUserId();
            if (userId == -1) throw new Exception();
            return new ResponseEntity<>(playlistService.getPlayList(userId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/playlist/{userID}")
    public ResponseEntity<List<PlaylistDTO>> getPlaylistByUserID(@PathVariable("userID") Long userId) {
        try {
            if (userId == -1) throw new Exception();
            return new ResponseEntity<>(playlistService.getUserPlaylist(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // 해당 음원이 저장된 플레이리스트 찾기
    @GetMapping("/playlist/saved/{songId}")
    public ResponseEntity<List<PlaylistDTO>> getSavedPlaylist(@PathVariable("songId") Long songId) {
        try {
            List<PlaylistDTO> playlistDTOList = playlistService.getSavedPlaylistBySongId(songId);
            System.out.println("저장된 플리 " + playlistDTOList.size());
            Long userId = SecurityUtil.getCurrentUserId();

            return new ResponseEntity<>(playlistDTOList, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 특정 플레이리스트 디테일 정보 가져오기
    @GetMapping("/playlist/detail/{playlistId}")
    public ResponseEntity<PlaylistDetailDTO> getPlaylistDetail(@PathVariable("playlistId") Long playlistId) throws Exception {
        try {
            PlaylistDetailDTO playlistDetailDTO = playlistService.getPlayListDetail(playlistId);

            Long userId = SecurityUtil.getCurrentUserId();

            return new ResponseEntity<>(playlistDetailDTO, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 플레이리스트 디테일 페이지에서 비슷한 플레이 리스트 가져오기
    @GetMapping("/playlist/similar/{playlistId}")
    public ResponseEntity<List<PlaylistDTO>> getSimilarPlaylist(@PathVariable("playlistId") Long playlistId) throws Exception {
        try {
            List<PlaylistDTO> playlistDTOList = playlistService.getSimilarPlaylist(playlistId);

            Long userId = SecurityUtil.getCurrentUserId();

            return new ResponseEntity<>(playlistDTOList, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 곡 디테일에서 비슷한 플레이 리스트 가져오기
    @GetMapping("/playlist/similar/song/{songId}")
    public ResponseEntity<List<PlaylistDTO>> getNewSimilarPlaylist(@PathVariable("songId") Long songId) throws Exception {
        try {
            List<PlaylistDTO> playlistDTOList = playlistService.getSimilarPlaylistBySong(songId);

            Long userId = SecurityUtil.getCurrentUserId();

            return new ResponseEntity<>(playlistDTOList, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    ////////////////////////   playlist update   ////////////////////////


    ////////////////////////   playlist delete   ////////////////////////


    ////////////////////////   playlist utils   ////////////////////////
    // 플레이리스트 좋아요 개수 가져오기
//    @GetMapping("/playlist/like/count/{playlistId}")
//    public ResponseEntity<Integer> getPlaylistLikeCnt(@PathVariable("playlistId") Long playlistId) {
//        try {
//            return new ResponseEntity<>(playlistService.getPlaylistLikeCnt(playlistId), HttpStatus.OK);
//        } catch (NoSuchElementException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("/playlist/validate/name/{playlistName}")
    public ResponseEntity<Boolean> validatePlaylistName(@PathVariable("playlistName") String playlistName) {
        try {
            return new ResponseEntity<>(playlistService.validPlaylistName(playlistName), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }
}
