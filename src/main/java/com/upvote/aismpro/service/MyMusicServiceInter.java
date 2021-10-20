package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.AlbumDTO;
import com.upvote.aismpro.entity.*;

import java.util.List;
import java.util.Map;

public interface MyMusicServiceInter{
    // like list 가져오기
    public List<Like> getLikeList(String userId);

    // create list 가져오기
    public List<Create> getCreateList(String userId);

    // buy list 가져오기
    public List<Buy> getBuyList(String userId);

    // sell list 가젼오기
    public List<Sell> getSellList(String userId);
}
