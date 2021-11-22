package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.GenreInfoDTO;
import com.upvote.aismpro.entity.GenreInfo;
import com.upvote.aismpro.entity.Keyword;
import com.upvote.aismpro.repository.GenreInfoRepository;
import com.upvote.aismpro.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.NotContextException;
import java.util.List;

@Service
@Transactional
public class ComposeService implements ComposeServiceInter {

    @Autowired
    private GenreInfoRepository genreInfoRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private CustomModelMapper modelMapper;


    public List<String> getGenreList() {
        List<String> genres = genreInfoRepository.findGenreQD();
        return genres;
    }

    public GenreInfoDTO getCategoryList(String genre) {
        GenreInfo genreInfo = genreInfoRepository.findByGenre(genre);
        return modelMapper.toGenreInfoDTO().map(genreInfo, GenreInfoDTO.class);
    }

    public List<String> get2ndList(String one) throws Exception {
        List<String> twos = keywordRepository.find2ndQD(one);
        if (twos.isEmpty()) throw new Exception();
        return twos;
    }

    public List<String> get3rdList(String one, String two) throws Exception {
        List<String> threes = keywordRepository.find3rdQD(one, two);
        if (threes.isEmpty()) throw new Exception();
        return threes;
    }

    public List<String> get4thList(String one, String two, String three) throws Exception {
        List<String> fours = keywordRepository.find4thQD(one, two, three);
        if (fours.isEmpty()) throw new Exception();
        return fours;
    }

    public List<String> get5thList(String one, String two, String three, String four) throws Exception {
        List<String> fives = keywordRepository.find5thQD(one, two, three, four);
        if (fives.isEmpty()) throw new Exception();
        return fives;
    }

    public List<String> get6thList(String one, String two, String three, String four, String five) throws Exception {
        List<String> sixs = keywordRepository.find6thQD(one, two, three, four, five);
        if (sixs.isEmpty()) throw new Exception();
        return sixs;
    }

}
