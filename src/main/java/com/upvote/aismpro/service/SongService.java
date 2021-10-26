package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SongService implements SongServiceInter {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private CustomModelMapper modelMapper;


    public SongDTO getSongDetail(String songId) {
        Optional<Song> songOpt = songRepository.findById(songId);
        Song song =  songOpt.orElseThrow(() -> new NoSuchElementException());
        return modelMapper.songMapper().map(song, SongDTO.class);
    }
}
