package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.MyAlbum;

import java.util.List;

public interface MyAlbumServiceInter{
    public List<MyAlbum> getSongs(String userID);

}
