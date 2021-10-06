package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.ComposeInfo;
import com.upvote.aismpro.entity.Compose;
import com.upvote.aismpro.service.ComposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ComposeController {

    @Autowired
    private ComposeService composeService;

    @GetMapping("/getEachComposeInfo")
    public Map<String, Object> getEachComposeInfo() {
        Map<String, Object> composeInfo = new LinkedHashMap<>();

        List<String> allGenre = composeService.getKeywords("Genre");
        List<String> allFirstMood = composeService.getKeywords("FirstMood");
        List<String> allSecondMood = composeService.getKeywords("SecondMood");

        composeInfo.put("Genre", allGenre);
        composeInfo.put("First_Mood", allFirstMood);
        composeInfo.put("Second_Mood", allSecondMood);

        return composeInfo;
    }

    // 작곡하기 키워드 받는 메서드
    @PostMapping("/compose")
    public Map<String, Object> compose(@RequestBody ComposeInfo composeInfo) throws InterruptedException {
        Map<String, Object> song_info = new HashMap<String, Object>();

        System.out.println(composeInfo);

        Thread.sleep(3000);

        song_info.put("result", "success");

        return song_info;
    }

    @GetMapping("/getAllGenre")
    public List<String> getAllGenre() {
        return composeService.getKeywords("Genre");
    }

    @GetMapping("/getFirstMood")
    public List<String> getFirstMoodByGenre(@RequestParam("genre") String genre) {
        return composeService.getFirstMoodByGenre(genre);
    }

    @GetMapping("/getSecondMood")
    public List<String> getSecondMoodByGenreAndFirstMood(@RequestParam("genre") String genre, @RequestParam("firstMood") String firstMood) {
        return composeService.getSecondMoodByFirstMood(genre, firstMood);
    }

    @GetMapping("/getSampleSound")
    public String[] getSampleSoundByKeywords(
            @RequestParam("genre") String genre,
            @RequestParam("firstMood") String firstMood,
            @RequestParam("secondMood") String secondMood) {
        return composeService.getSampleSoundByKeywords(genre, firstMood, secondMood);
    }

    @PostMapping("/uploadImg")
    public Map<String, Object> uploadImg(@RequestParam("file") MultipartFile file) throws IOException {
        Map<String, Object> map = new HashMap<>();

        String imgName = file.getOriginalFilename();
        String path = "/Users/BaekGyu/Intellij-workspace/AISM_PRO/AISM_PRO_FR/src/components/content/image/song/" + imgName;
        File dst = new File(path);
        
        try {
            file.transferTo(dst);

            map.put("img", imgName);
            map.put("result", true);
        }catch (Exception e) {
            e.printStackTrace();

            map.put("result", false);
        }

        return map;
    }
}