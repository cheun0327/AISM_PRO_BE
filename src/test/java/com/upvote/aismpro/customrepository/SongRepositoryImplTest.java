package com.upvote.aismpro.customrepository;

import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.repository.SongRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SongRepositoryImplTest {

    @Autowired
    private SongRepository songRepository;

    @Test
    void isEnough() {

        boolean isEnough = songRepository.isEnoughAddToPlaylistQD(60L);

        System.out.println(isEnough);
    }

    @Test
    void findSongByUserId() {

        List<Song> songList =  songRepository.findSongListByUserIdLimit3QD(null);
        songList.forEach(song -> System.out.println(song.getSongId()));
    }
}