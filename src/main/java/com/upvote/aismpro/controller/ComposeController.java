package com.upvote.aismpro.controller;


import com.upvote.aismpro.dto.GenreInfoDTO;
import com.upvote.aismpro.dto.KeywordListResDTO;
import com.upvote.aismpro.service.ComposeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ComposeController {

    private final ComposeService composeService;

    @GetMapping("/compose/category/{genre}")
    public ResponseEntity<GenreInfoDTO> getCategory(@PathVariable("genre") String genre) {
        try {
            return new ResponseEntity<>(composeService.getCategoryList(genre), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation("장르 리스트 가져오기")
    @GetMapping("/compose")
    public ResponseEntity<List<String>> get1st() {
        try {
            return new ResponseEntity<>(composeService.getGenreList(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "키워드 ID로 하위 키워드 찾기")
    @GetMapping("/keywords")
    public KeywordListResDTO findChildKeyword(@RequestParam("ids") List<Long> selectedKeywordIdList) {
        return composeService.findChildKeyword(selectedKeywordIdList);
    }

    @GetMapping("/compose/{one}")
    public ResponseEntity<List<String>> get2nd(@PathVariable("one") String one) {
        try {
            return new ResponseEntity<>(composeService.get2ndList(one), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/compose/{one}/{two}")
    public ResponseEntity<List<String>> get3rd(@PathVariable("one") String one, @PathVariable("two") String two) {
        try {
            return new ResponseEntity<>(composeService.get3rdList(one, two), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/compose/{one}/{two}/{three}")
    public ResponseEntity<List<String>> get4th(@PathVariable("one") String one, @PathVariable("two") String two, @PathVariable("three") String three) {
        try {
            return new ResponseEntity<>(composeService.get4thList(one, two, three), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/compose/{one}/{two}/{three}/{four}")
    public ResponseEntity<List<String>> get5th(@PathVariable("one") String one, @PathVariable("two") String two,
                                               @PathVariable("three") String three, @PathVariable("four") String four) {
        try {
            return new ResponseEntity<>(composeService.get5thList(one, two, three, four), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/compose/{one}/{two}/{three}/{four}/{five}")
    public ResponseEntity<List<String>> get6th(@PathVariable("one") String one, @PathVariable("two") String two,
                                               @PathVariable("three") String three, @PathVariable("four") String four, @PathVariable("five") String five) {
        try {
            return new ResponseEntity<>(composeService.get6thList(one, two, three, four, five), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
