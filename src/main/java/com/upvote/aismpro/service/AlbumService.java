package com.upvote.aismpro.service;


import com.upvote.aismpro.entity.Album;
import com.upvote.aismpro.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    public List<Album> getAlbum () {
        List<Album> albums = albumRepository.findAll();

        return albums;
    }
}
