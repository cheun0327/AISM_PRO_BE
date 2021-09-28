package com.upvote.aismpro.controller;

import com.upvote.aismpro.service.ComposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ComposeController {

    @Autowired
    private ComposeService composeService;

    @GetMapping("/getEachComposeInfo")
    public Map<String, Object> getEachComposeInfo() {
        Map<String, Object> composeInfo = new HashMap<>();

        List<String> allGenre = composeService.getAllGenre();
        List<String> allFirstMood = composeService.getFirstMood();
        List<String> allSecondMood = composeService.getSecondMood();

        composeInfo.put("Genre", allGenre);
        composeInfo.put("First_Mood", allFirstMood);
        composeInfo.put("Second_Mood", allSecondMood);

        return composeInfo;
    }
}