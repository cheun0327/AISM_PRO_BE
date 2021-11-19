package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.GenreInfo;
import com.upvote.aismpro.repository.GenreInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ComposeService implements ComposeServiceInter {

    @Autowired
    private GenreInfoRepository genreInfoRepository;

    public List<String> getGenreList() {
        List<String> genres = genreInfoRepository.findGenreQD();
        return genres;
    }

}
