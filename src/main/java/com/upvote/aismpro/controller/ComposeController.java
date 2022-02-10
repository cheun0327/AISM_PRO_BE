package com.upvote.aismpro.controller;


import com.upvote.aismpro.dto.GenreInfoDTO;
import com.upvote.aismpro.service.ComposeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ComposeController {

    private final ComposeService composeService;

    @ApiOperation(value = "해당 장르의 카테고리 가져오기", notes = "ex) 카테고리, 서브카테고리, 키워드, 효과음")
    @GetMapping("/compose/category/{genre}")
    public GenreInfoDTO getCategory(@PathVariable("genre") String genre) {
        return composeService.getCategoryList(genre);
    }

    @ApiOperation("장르 리스트 가져오기")
    @GetMapping("/compose")
    public List<String> getGenres() {
        return composeService.getGenreList();
    }

    @ApiOperation(value = "첫번째 카테고리의 옵션들 가져오기", notes = "ex) 운동, 일, 추억")
    @GetMapping("/compose/{genre}")
    public List<String> get1stOptions(@PathVariable("genre") String genre) {
        return composeService.get1stOptions(genre);
    }

    @ApiOperation(value = "두번째 카테고리의 옵션들 가져오기", notes = "ex) 주말 낮..., 영국 기숙사...,")
    @GetMapping("/compose/{genre}/{category}")
    public List<String> get2ndOptions(
            @PathVariable("genre") String genre,
            @PathVariable("category") String category
    ) {
        return composeService.get2ndOptions(genre, category);
    }

    @ApiOperation(value = "세번째 카테고리의 옵션들 가져오기", notes = "ex) 피아노, 일렉피아노, 낭만적이다, 흥겹다")
    @GetMapping("/compose/{genre}/{category}/{subCategory}")
    public List<String> get3rdOptions(
            @PathVariable("genre") String genre,
            @PathVariable("category") String category,
            @PathVariable("subCategory") String subCategory
    ) {
        return composeService.get3rdOptions(genre, category, subCategory);
    }

    @ApiOperation(value = "네번째 카테고리의 옵션들 가져오기", notes = "ex) 귀뚜라미 소리, 풀벌레 소리, 없음")
    @GetMapping("/compose/{genre}/{category}/{subCategory}/{keyword}")
    public List<String> get4thOptions(
            @PathVariable("genre") String genre,
            @PathVariable("category") String category,
            @PathVariable("subCategory") String subCategory,
            @PathVariable("keyword") String keyword
    ) {
        return composeService.get4thOptions(genre, category, subCategory, keyword);
    }

    @ApiOperation(value = "키워드 최종 선택했을 경우")
    @GetMapping("/compose/{one}/{two}/{three}/{four}/{five}")
    public List<?> returnNullList(
            @PathVariable("one") String one,
            @PathVariable("two") String two,
            @PathVariable("three") String three,
            @PathVariable("four") String four,
            @PathVariable("five") String five
    ) {
        return Collections.singletonList(null);
    }
}
