package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.entity.*;

import java.util.List;

public interface MyMusicServiceInter{
    // like list 가져오기
    public List<LikeDTO> getLikeList(String userId);

    // create list 가져오기
    public List<CreateDTO> getCreateList(String userId);

    // buy list 가져오기
    public List<BuyDTO> getBuyList(String userId);

    // sell list 가져오기
    public List<SellDTO> getSellList(String userId);

    // play list 가져오기
    public List<PlayList> getPlayList(String userId);
}
