package com.upvote.aismpro.controller;

import com.upvote.aismpro.entity.FourFive;
import com.upvote.aismpro.entity.OneTwo;
import com.upvote.aismpro.repository.FourFiveRepository;
import com.upvote.aismpro.repository.OneTwoRepository;
import com.upvote.aismpro.repository.ThreeFourRepository;
import com.upvote.aismpro.repository.TwoThreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.Tuple;
import java.util.List;

@RestController
public class ComposeController {

    @Autowired
    private OneTwoRepository oneTwoRepository;
    @Autowired
    private TwoThreeRepository twoThreeRepository;
    @Autowired
    private ThreeFourRepository threeFourRepository;
    @Autowired
    private FourFiveRepository fourFiveRepository;

    @GetMapping("/compose/test")
    public ResponseEntity<List<String>> test() {
        List<String> ones = oneTwoRepository.findTwoQD();
        List<String> twos = oneTwoRepository.findTwoByOneQD("Newage");
        List<String> threes = twoThreeRepository.findThreeByTwoQD("명상");

        return new ResponseEntity<>(threes, HttpStatus.OK);
    }

    // 길이는 프론트에서 처리
    // findOneQD
    @GetMapping("/compose/one")
    public ResponseEntity<List<String>> one() {
        List<String> ones = oneTwoRepository.findOneQD();
        return new ResponseEntity<>(ones, HttpStatus.OK);
    }
    // findtwoByOneQD
    @GetMapping("/compose/two/{one}")
    public ResponseEntity<List<String>> two(@PathVariable String one) {
        List<String> twos = oneTwoRepository.findTwoByOneQD(one);
        return new ResponseEntity<>(twos, HttpStatus.OK);
    }
    // findThreeByTwoQD
    @GetMapping("/compose/three/{two}")
    public ResponseEntity<List<String>> three(@PathVariable String two) {
        List<String> threes = twoThreeRepository.findThreeByTwoQD(two);
        return new ResponseEntity<>(threes, HttpStatus.OK);
    }
    // findFourByThreeQD
    @GetMapping("/compose/four/{three}")
    public ResponseEntity<List<String>> four(@PathVariable String three) {
        List<String> fours = threeFourRepository.findFourByThreeQD(three);
        return new ResponseEntity<>(fours, HttpStatus.OK);
    }
    // findFiveByFourQD
    @GetMapping("/compose/five/{four}")
    public ResponseEntity<List<String>> five(@PathVariable String four) {
        List<String> fives = fourFiveRepository.findFiveByFourQD(four);
        return new ResponseEntity<>(fives, HttpStatus.OK);
    }
}
