package com.upvote.aismpro.service;

import com.upvote.aismpro.repository.ComposeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComposeService implements ComposeServiceInter {

    @Autowired
    private ComposeRepository composeRepository;

    @Override
    // 모든 장르 가져오기
    public List<String> getAllGenre() {
        return composeRepository.findAllGenre();
    }

    // 모든 1번 분위기 가져오기
    @Override
    public List<String> getFirstMood() {
        return composeRepository.findAllFirstMood();
    }
    // 모든 2번 분위기 가져오기
    @Override
    public List<String> getSecondMood() {
        return composeRepository.findAllSecondMood();
    }

    // 장르 -> 첫번째 분위기 가져오기
    @Override
    public List<String> getFirstMoodByGenre(String genre) {
        return composeRepository.findFirstMoodByGenre(genre);
    }

    // 장르 & 첫번째 분위기 -> 두번째 분위기 가져오기
    @Override
    public List<String> getSecondMoodByFirstMood(String genre, String firstMood) {
        return composeRepository.findSecondMoodByFirstMood(genre, firstMood);
    }
    // 장르 & 첫번째 분위기 & 두번째 분위기 -> 샘플 사운드 가져오기
    @Override
    public String[] getSampleSoundByKeywords(String genre, String firstMood, String secondMood) {
        return composeRepository.findSampleSoundByKeywords(genre, firstMood, secondMood).split(",");
    }
}
