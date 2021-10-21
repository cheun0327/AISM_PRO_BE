package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.entity.*;
import com.upvote.aismpro.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;

import static com.upvote.aismpro.entity.QCreate.create;

@Service
@Transactional
public class MyMusicService implements MyMusicServiceInter{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreateRepository createRepository;

    @Autowired
    private BuyRepository buyRepository;

    @Autowired
    private SellRepository sellRepository;

    @Autowired
    private  LikeRepository likeRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private CustomModelMapper modelMapper;

    // like list 가져오기
    @Override
    public List<LikeDTO> getLikeList(String userId) {
        return userRepository.getById(userId).getLikes()
                .stream().map(like -> modelMapper.likeMapper().map(like, LikeDTO.class))
                .collect(Collectors.toList());
    }

    // create list 가져오기
    @Override
    public List<CreateDTO> getCreateList(String userId) {
        return userRepository.getById(userId).getCreates()
                .stream().map(create -> modelMapper.createMapper().map(create, CreateDTO.class))
                .collect(Collectors.toList());
    }

    // buy list 가져오기
    @Override
    public List<BuyDTO> getBuyList(String userId) {
        return userRepository.getById(userId).getBuys()
                .stream().map(buy -> modelMapper.buyMapper().map(buy, BuyDTO.class))
                .collect(Collectors.toList());
    }

    // sell list 가젼오기
    @Override
    public List<SellDTO> getSellList(String userId) {
        return userRepository.getById(userId).getSells()
                .stream().map(sell -> modelMapper.sellMapper().map(sell, SellDTO.class))
                .collect(Collectors.toList());
    }

    // play list 가져오기
    @Override
    public List<PlaylistDTO> getPlayList(String userId) {
        return userRepository.getById(userId).getPlaylists()
                .stream().map(pl -> modelMapper.playlistMapper().map(pl, PlaylistDTO.class))
                .collect(Collectors.toList());
    }

    // playlist detail 가져오가
    @Override
    public PlaylistDetailDTO getPlayListDetail(String playlistId) {
        return modelMapper.playlistDetailMapper().map(playlistRepository.getById(playlistId), PlaylistDetailDTO.class);
    }
}
