package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.AlbumDTO;
import com.upvote.aismpro.entity.*;
import com.upvote.aismpro.repository.*;
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

    private ModelMapper modelMapper = new ModelMapper();

    // like list 가져오기
    @Override
    public List<Like> getLikeList(String userId) {
        return userRepository.getById(userId).getLikes();
    }

    // create list 가져오기
    @Override
    public List<Create> getCreateList(String userId) {
        return userRepository.getById(userId).getCreates();
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
}
