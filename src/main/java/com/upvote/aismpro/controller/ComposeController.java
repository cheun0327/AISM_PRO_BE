package com.upvote.aismpro.controller;


import com.upvote.aismpro.dto.GenreInfoDTO;
import com.upvote.aismpro.entity.GenreInfo;
import com.upvote.aismpro.service.ComposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ComposeController {

    @Autowired
    private ComposeService composeService;

    @GetMapping("/compose/category/{genre}")
    public ResponseEntity<GenreInfoDTO> getCategory(@PathVariable("genre") String genre) {
        try {
            return new ResponseEntity<>(composeService.getCategoryList(genre), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/compose")
    public ResponseEntity<List<String>> get1st() {
        try{
            return new ResponseEntity<>(composeService.getGenreList(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/compose/{one}")
    public ResponseEntity<List<String>> get2nd(@PathVariable("one") String one){
        try{
            return new ResponseEntity<>(composeService.get2ndList(one), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/compose/{one}/{two}")
    public ResponseEntity<List<String>> get3rd(@PathVariable("one") String one, @PathVariable("two") String two){
        try{
            return new ResponseEntity<>(composeService.get3rdList(one, two), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/compose/{one}/{two}/{three}")
    public ResponseEntity<List<String>> get4th(@PathVariable("one") String one, @PathVariable("two") String two, @PathVariable("three") String three){
        try{
            return new ResponseEntity<>(composeService.get4thList(one, two, three), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/compose/{one}/{two}/{three}/{four}")
    public ResponseEntity<List<String>> get5th(@PathVariable("one") String one, @PathVariable("two") String two,
                       @PathVariable("three") String three, @PathVariable("four") String four){
        try{
            return new ResponseEntity<>(composeService.get5thList(one, two, three, four), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/compose/{one}/{two}/{three}/{four}/{five}")
    public ResponseEntity<List<String>> get6th(@PathVariable("one") String one, @PathVariable("two") String two,
                       @PathVariable("three") String three, @PathVariable("four") String four, @PathVariable("five") String five){
        try{
            return new ResponseEntity<>(composeService.get6thList(one, two, three, four, five), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
