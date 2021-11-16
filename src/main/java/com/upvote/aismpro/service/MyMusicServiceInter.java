package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.entity.*;

import java.util.List;

public interface MyMusicServiceInter{
    // like list 가져오기
    public List<LikeDTO> getLikeList(String userId) throws Exception;

    // create list 가져오기
    public List<CreateDTO> getCreateList(String userId) throws Exception;

    // buy list 가져오기
    public List<BuyDTO> getBuyList(String userId) throws Exception;

    // sell list 가져오기
    public List<SellDTO> getSellList(String userId) throws Exception;

    // play list 가져오기
    public List<PlaylistDTO> getPlayList(String userId) throws Exception;
    public List<NewPlaylistDTO> getNewPlayList(String userId) throws Exception;

    // playlist detail 가져오가
    public PlaylistDetailDTO getPlayListDetail(String playlistId) throws Exception;
}
