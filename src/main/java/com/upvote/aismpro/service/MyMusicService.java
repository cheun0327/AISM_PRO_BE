package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.CreateDTO;
import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.entity.*;
import com.upvote.aismpro.repository.*;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.LikeDTO;

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
    public List<LikeDTO> getLikeList(String userId) throws Exception {
        try {
            return userRepository.getById(userId).getLikes()
                    .stream().map(like -> modelMapper.likeMapper().map(like, LikeDTO.class))
                    .collect(Collectors.toList());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new NoSuchElementException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    // create list 가져오기
    @Override
    public List<CreateDTO> getCreateList(String userId) throws Exception {
        try{
            return userRepository.getById(userId).getCreates()
                    .stream().map(create -> modelMapper.createMapper().map(create, CreateDTO.class))
                    .collect(Collectors.toList());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new NoSuchElementException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    // buy list 가져오기
    @Override
    public List<BuyDTO> getBuyList(String userId) throws Exception {
        try{
            return userRepository.getById(userId).getBuys()
                    .stream().map(buy -> modelMapper.buyMapper().map(buy, BuyDTO.class))
                    .collect(Collectors.toList());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new NoSuchElementException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    // sell list 가져오기
    @Override
    public List<SellDTO> getSellList(String userId) throws Exception {
        try{
            return userRepository.getById(userId).getSells()
                    .stream().map(sell -> modelMapper.sellMapper().map(sell, SellDTO.class))
                    .collect(Collectors.toList());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new NoSuchElementException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    // play list 가져오기
    @Override
    public List<PlaylistDTO> getPlayList(String userId) throws Exception {
        try{
            return userRepository.getById(userId).getPlaylists()
                    .stream().map(pl -> modelMapper.playlistMapper().map(pl, PlaylistDTO.class))
                    .collect(Collectors.toList());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new NoSuchElementException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    // playlist detail 가져오기
    @Override
    public PlaylistDetailDTO getPlayListDetail(String playlistId) throws Exception {
        try {
            return modelMapper.playlistDetailMapper().map(playlistRepository.getById(playlistId), PlaylistDetailDTO.class);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new NoSuchElementException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }
}
