package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.AddSongListDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.util.Arrays;

@SpringBootTest
class PlaylistServiceTest {

    @Autowired
    private PlaylistService playlistService;

    @Transactional
    @Test
    void addSongList() {

        AddSongListDTO dto = new AddSongListDTO();
        dto.setPlaylistId(69L);
        dto.setSongIdList(Arrays.asList(1L, 2L, 3L));

        ResponseEntity<Object> httpStatusResponseEntity = playlistService.addSongList(dto);
        System.out.println(httpStatusResponseEntity);
    }
}