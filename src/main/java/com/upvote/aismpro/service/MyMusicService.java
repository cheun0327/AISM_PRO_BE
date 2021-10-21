package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.CreateDTO;
import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.BuyDTO;
import com.upvote.aismpro.dto.CreateDTO;
import com.upvote.aismpro.dto.LikeDTO;
import com.upvote.aismpro.dto.SellDTO;
import com.upvote.aismpro.entity.*;
import com.upvote.aismpro.repository.*;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.LikeDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

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
    private CustomModelMapper modelMapper;

    // like list 가져오기
    @Override
    public List<LikeDTO> getLikeList(String userId) {
        List<LikeDTO> likes = new ArrayList<>();

        for (Like like : userRepository.getById(userId).getLikes()) {
            likes.add(modelMapper.likeMapper().map(like, LikeDTO.class));
        }

        return likes;
    }

    // create list 가져오기
    @Override
    public List<CreateDTO> getCreateList(String userId) {
        List<CreateDTO> creates = new ArrayList<>();

        for (Create create : userRepository.getById(userId).getCreates()) {
            creates.add(modelMapper.createMapper().map(create, CreateDTO.class));
        }

        return creates;
    }

    // buy list 가져오기
    @Override
    public List<BuyDTO> getBuyList(String userId) {
        List<BuyDTO> buys = new ArrayList<>();

        for (Buy buy : userRepository.getById(userId).getBuys()) {
            buys.add(modelMapper.buyMapper().map(buy, BuyDTO.class));
        }
        return buys;
    }

    // sell list 가져오기
    @Override
    public List<SellDTO> getSellList(String userId) {
        List<SellDTO> sells = new ArrayList<>();

        for (Sell sell : userRepository.getById(userId).getSells()) {
            sells.add(modelMapper.buyMapper().map(sell, SellDTO.class));
        }
        return sells;
    }

    // play list 가져오기
    @Override
    public List<PlayList> getPlayList(String userId) {
        return userRepository.getById(userId).getPlaylists();
    }
}
