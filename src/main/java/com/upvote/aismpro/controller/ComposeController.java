package com.upvote.aismpro.controller;


import com.upvote.aismpro.service.ComposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ComposeController {

    @Autowired
    private ComposeService composeService;

    @GetMapping("/compose")
    public ResponseEntity<List<String>> getGenreList() {
        try{
            return new ResponseEntity<>(composeService.getGenreList(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
