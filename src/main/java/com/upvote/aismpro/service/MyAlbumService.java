package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.MyAlbum;
import com.upvote.aismpro.repository.MyAlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyAlbumService implements MyAlbumServiceInter{

    @Autowired
    private MyAlbumRepository myAlbumRepository;

    @Override
    public List<MyAlbum> getSongs(String userID){
        return myAlbumRepository.findByUserID(userID);
    }
}
