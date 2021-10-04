package com.upvote.aismpro.service;

import java.util.List;

public interface ComposeServiceInter {
    // 키워드 가져오기
    public List<String> getKeywords(String keyword);

    // 장르 -> 첫번째 분위기 가져오기
    public List<String> getFirstMoodByGenre(String genre);

    // 장르 & 첫번째 분위기 -> 두번째 분위기 가져오기
    public List<String> getSecondMoodByFirstMood(String genre, String firstMood);

    // 장르 & 첫번째 분위기 & 두번째 분위기 -> 샘플 사운드 가져오기
    public String[] getSampleSoundByKeywords(String genre, String firstMood, String secondMood);
}
