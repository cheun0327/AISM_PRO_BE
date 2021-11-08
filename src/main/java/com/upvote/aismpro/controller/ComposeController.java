package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.ComposeInfoDTO;
import com.upvote.aismpro.service.ComposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
public class ComposeController {

    @Autowired
    private ComposeService composeService;

    @GetMapping("/compose")
    public Map<String, Object> getEachComposeInfo() {
        Map<String, Object> composeInfo = new LinkedHashMap<>();

        List<String> allGenre = composeService.getKeywords("genre");
        List<String> allFirstMood = composeService.getKeywords("firstMood");
        List<String> allSecondMood = composeService.getKeywords("secondMood");

        composeInfo.put("genre", allGenre);
        composeInfo.put("firstMood", allFirstMood);
        composeInfo.put("secondMood", allSecondMood);

        return composeInfo;
    }

    // 작곡하기 키워드 받는 메서드
    @PostMapping("/compose")
    public Map<String, Object> compose(@RequestBody ComposeInfoDTO composeInfo) throws InterruptedException {
        Map<String, Object> song_info = new HashMap<String, Object>();

        Thread.sleep(3000);

        song_info.put("result", "success");

        return song_info;
    }

    @GetMapping("/compose/genre")
    public List<String> getAllGenre() {
        return composeService.getKeywords("genre");
    }

    @GetMapping("/compose/first-mood")
    public List<String> getFirstMoodByGenre(@RequestParam("genre") String genre) {
        return composeService.getFirstMoodByGenre(genre);
    }

    @GetMapping("/compose/second-mood")
    public List<String> getSecondMoodByGenreAndFirstMood(@RequestParam("genre") String genre, @RequestParam("firstMood") String firstMood) {
        return composeService.getSecondMoodByFirstMood(genre, firstMood);
    }

    @GetMapping("/compose/sample-sound")
    public String[] getSampleSoundByKeywords(
            @RequestParam("genre") String genre,
            @RequestParam("firstMood") String firstMood,
            @RequestParam("secondMood") String secondMood) {
        return composeService.getSampleSoundByKeywords(genre, firstMood, secondMood);
    }

    @PostMapping("/compose/img/{userId}")
    public Map<String, Object> uploadImg(
            @PathVariable("userId") String userId,
            @RequestParam("file") MultipartFile file) throws IOException {
        Map<String, Object> map = new HashMap<>();

        String imgName = file.getOriginalFilename();
        String userId_withoutHyphen = userId.replaceAll("-", "");

        String rootPath = "/var/lib/jenkins/workspace/AISM_PRO_REACT/src/components/content/image/user/" + userId_withoutHyphen;
        List<String> dirs = Arrays.asList("compose", "song");

        Path dstDir = Paths.get(rootPath + "/" + String.join("/", dirs));
        String dstPath = rootPath + "/" + String.join("/", dirs) + "/" + imgName;

        try {
            Files.createDirectories(dstDir);

            File dst = new File(dstPath);
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
