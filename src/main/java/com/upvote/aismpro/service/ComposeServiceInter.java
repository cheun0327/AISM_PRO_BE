package com.upvote.aismpro.service;

import java.util.List;

public interface ComposeServiceInter {
    // 모든 장르 가져오기
    public List<String> getAllGenre();

    // 모든 1번 분위기 가져오기
    public List<String> getFirstMood();

    // 모든 2번 분위기 가져오기
    public List<String> getSecondMood();

    // 장르 -> 첫번째 분위기 가져오기
    public List<String> getFirstMoodByGenre(String genre);

    // 장르 & 첫번째 분위기 -> 두번째 분위기 가져오기
    public List<String> getSecondMoodByFirstMood(String genre, String firstMood);

    // 장르 & 첫번째 분위기 & 두번째 분위기 -> 샘플 사운드 가져오기
    public String[] getSampleSoundByKeywords(String genre, String firstMood, String secondMood);
}
