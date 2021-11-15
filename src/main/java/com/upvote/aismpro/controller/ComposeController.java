package com.upvote.aismpro.controller;

import com.upvote.aismpro.entity.OneTwo;
import com.upvote.aismpro.repository.OneTwoRepository;
import com.upvote.aismpro.repository.TwoThreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.Tuple;
import java.util.List;

@RestController
public class ComposeController {

    @Autowired
    private OneTwoRepository oneTwoRepository;
    @Autowired
    private TwoThreeRepository twoThreeRepository;

    @GetMapping("/compose/test")
    public ResponseEntity<List<String>> test() {
        List<String> ones = oneTwoRepository.findTwoQD();
        List<String> twos = oneTwoRepository.findTwoByOneQD("Newage");
        List<String> threes = twoThreeRepository.findThreeByTwoQD("명상");

        return new ResponseEntity<>(threes, HttpStatus.OK);
    }
}
