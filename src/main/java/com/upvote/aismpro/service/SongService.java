package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongService implements SongServiceInter {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private CustomModelMapper modelMapper;


    public SongDTO getSongDetail(String songId){
        return modelMapper.songMapper().map(songRepository.getById(songId), SongDTO.class);
    }
}
