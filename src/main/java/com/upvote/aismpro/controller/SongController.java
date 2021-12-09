package com.upvote.aismpro.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.dto.SongSaveDTO;
import com.upvote.aismpro.vo.SongSaveVO;
import com.upvote.aismpro.dto.SongTagDTO;
import com.upvote.aismpro.security.SecurityUtil;
import com.upvote.aismpro.service.PlaylistService;
import com.upvote.aismpro.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
public class SongController {

    @Autowired
    private SongService songService;
    @Autowired
    private PlaylistService playlistService;

    ////////////////////////   song create => MEMBER(credit>0)   ////////////////////////
    // song 생성 => 생성 가능 권한 확인
    @PostMapping("/song")
    public ResponseEntity<Long> createSong(@ModelAttribute SongSaveVO songVO){
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SongDTO song = new SongDTO();
        try {
            Long userId = SecurityUtil.getCurrentUserId();
            if (userId < 0) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

            SongSaveDTO songDTO = mapper.readValue(songVO.getVal(), SongSaveDTO.class);

            // song 기본 정보 저장
            song = songService.saveSong(songDTO, songVO.getImg());

            // song wav file tmp에서 이동
            songService.moveSongWavFile(song.getSongId());

            return new ResponseEntity<>(song.getSongId(), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            if (song != null) songService.deleteSong(song.getSongId());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // song 저장 => 저장 권한 확인 후 저장


    ////////////////////////   song read => GUEST && MEMBER   ////////////////////////
    // Song Detail With Like
    @GetMapping("/song/{songId}")
    public ResponseEntity<SongDTO> getSongDetail(@PathVariable("songId") Long songId) {
        try{
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
        try{
            Long userId = SecurityUtil.getCurrentUserId();

            List<SongDTO> songDTOList = songService.getSimilarSong(songId);
            if (userId != -1) {
                songDTOList = songService.setLike2SongDTOList(songDTOList, userId);
            }

            return new ResponseEntity<>(songDTOList, HttpStatus.OK);
        }
        catch (Exception e) {
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

            if (userId != -1) {
                songDTOList = songService.setLike2SongDTOList(songDTOList, userId);
                playlistDTOList = playlistService.setLike2PlaylistDTOList(playlistDTOList, userId);
            }

            map.put("song", songDTOList);
            map.put("playlist", playlistDTOList);

            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        catch (Exception e) {
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

}
