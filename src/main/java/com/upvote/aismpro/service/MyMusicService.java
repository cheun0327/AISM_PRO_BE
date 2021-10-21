package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.AlbumDTO;
import com.upvote.aismpro.dto.CreateDTO;
import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.dto.PlaylistInfoDTO;
import com.upvote.aismpro.entity.*;
import com.upvote.aismpro.repository.*;
import com.upvote.aismpro.settings.ModelMapperConfig;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;

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

    private ModelMapperConfig modelMapper = new ModelMapperConfig();

    // like list 가져오기
    @Override
    public List<Like> getLikeList(String userId) {
        return userRepository.getById(userId).getLikes();
    }

    // create list 가져오기
    @Override
    public List<Create> getCreateList(String userId) {
        List<Create> create_entities = userRepository.getById(userId).getCreates();

        ModelMapper createMapper = modelMapper.createMapper();

        for (Create entity : create_entities) {
            CreateDTO createDTO = createMapper.map(entity, CreateDTO.class);
            createDTO.print();
        }

        return create_entities;
    }

    // buy list 가져오기
    @Override
    public List<Buy> getBuyList(String userId) {
        return userRepository.getById(userId).getBuys();
    }

    // sell list 가젼오기
    @Override
    public List<Sell> getSellList(String userId) {
        return userRepository.getById(userId).getSells();
    }

    // play list 가져오기
    @Override
    public List<PlayList> getPlayList(String userId) {
        return userRepository.getById(userId).getPlaylists();
    }
}
