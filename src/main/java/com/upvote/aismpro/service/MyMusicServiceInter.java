package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.AlbumDTO;
import com.upvote.aismpro.dto.CreateDTO;
import com.upvote.aismpro.dto.LikeDTO;
import com.upvote.aismpro.dto.PlaylistInfoDTO;
import com.upvote.aismpro.entity.*;

import java.util.List;
import java.util.Map;

public interface MyMusicServiceInter{
    // like list 가져오기
    public List<LikeDTO> getLikeList(String userId);

    // create list 가져오기
    public List<CreateDTO> getCreateList(String userId);

    // buy list 가져오기
    public List<Buy> getBuyList(String userId);

    // sell list 가져오기
    public List<Sell> getSellList(String userId);

    // play list 가져오기
    public List<PlayList> getPlayList(String userId);
}
