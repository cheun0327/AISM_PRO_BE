package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.MyAlbum;

import java.util.List;
import java.util.Map;

public interface MyAlbumServiceInter{
    public List<Map<String, Object>> getAllSong(String userID, String option);

}
