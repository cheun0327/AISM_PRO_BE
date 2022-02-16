package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.GenreInfoDTO;
import com.upvote.aismpro.entity.Genre;
import com.upvote.aismpro.entity.GenreOptionOrder;
import com.upvote.aismpro.repository.DummyKeywordPathRepository;
import com.upvote.aismpro.repository.GenreOptionOrderRepository;
import com.upvote.aismpro.repository.GenreRepository;
import com.upvote.aismpro.repository.KeywordPathRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ComposeService {

    private final GenreRepository genreRepository;
    private final GenreOptionOrderRepository genreOptionOrderRepository;
    private final KeywordPathRepository keywordPathRepository;
    private final DummyKeywordPathRepository dummyKeywordPathRepository;

    private final static List<String> implGenres = Arrays.asList("Jazz", "Pop", "Newage");

    public GenreInfoDTO getCategoryList(String genre) {
        List<GenreOptionOrder> categoryList = genreOptionOrderRepository.findAllByGenreName(genre);

        GenreInfoDTO responseDTO = new GenreInfoDTO();

        Map<Integer, Consumer<String>> setterMapper = new HashMap<>();
        setterMapper.put(1, responseDTO::setTwo);
        setterMapper.put(2, responseDTO::setThree);
        setterMapper.put(3, responseDTO::setFour);
        setterMapper.put(4, responseDTO::setFive);
        setterMapper.put(5, responseDTO::setSix);

        responseDTO.setOne("장르");
        responseDTO.setGenre(genre);
        responseDTO.setCategoryCnt(categoryList.size() + 1);
        categoryList.forEach(option -> setterMapper.get(option.getOrder()).accept(option.getGenreOption().getOptionName()));

        return responseDTO;
    }

    public List<String> getGenreList() {
        return genreRepository.findAll(Sort.by(Sort.DEFAULT_DIRECTION, "genreOrder")).stream()
                .map(Genre::getGenreName)
                .collect(Collectors.toList());
    }

    public List<String> get1stOptions(String genre) {

        if (implGenres.contains(genre)) {
            return keywordPathRepository.find1stOptions(genre);
        }

        return dummyKeywordPathRepository.find1stDummyOptions();
    }

    public List<String> get2ndOptions(String genre, String category) {

        if (implGenres.contains(genre)) {
            return keywordPathRepository.find2ndOptions(genre, category);
        }

        return dummyKeywordPathRepository.find2ndDummyOptions(category);
    }

    public List<String> get3rdOptions(String genre, String category, String subCategory) {

        if (implGenres.contains(genre)) {
            return keywordPathRepository.find3rdOptions(genre, category, subCategory);
        }

        return dummyKeywordPathRepository.find3rdDummyOptions(category, subCategory);
    }

    public List<String> get4thOptions(String genre, String category, String subCategory, String keyword) {

        if (implGenres.contains(genre)) {
            return keywordPathRepository.find4thOptions(genre, category, subCategory, keyword).stream()
                    .map(keywordPath -> keywordPath.getFx().getKeyword())
                    .collect(Collectors.toList());
        }

        return Collections.singletonList(null);
    }
}
