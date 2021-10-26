package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.LikeDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.entity.Like;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.repository.LikeRepository;
import com.upvote.aismpro.repository.SongRepository;
import com.upvote.aismpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SongService implements SongServiceInter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private CustomModelMapper modelMapper;

    @Override
    public SongDTO getSongDetail(String songId) {
        Optional<Song> songOpt = songRepository.findById(songId);
        Song song =  songOpt.orElseThrow(() -> new NoSuchElementException());
        return modelMapper.songMapper().map(song, SongDTO.class);
    }

    @Override
    public Integer getLikeCnt(String songId){
        Integer cnt = likeRepository.countBySong_SongId(songId);
        return cnt;
    }

    @Override
    public void creatLike(String userId, String songId) {
        Like like = new Like(userRepository.getById(userId), songRepository.getById(songId));
        likeRepository.save(like);
    }

    @Override
    public void deleteLike(String likeId) {
        likeRepository.deleteById(likeId);
    }
}
