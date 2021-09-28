package com.upvote.aismpro.service;

import java.util.List;

public interface ComposeServiceInter {
    // 모든 장르 가져오기
    public List<String> getAllGenre();

    // 모든 1번 분위기 가져오기
    public List<String> getFirstMood();

    // 모든 2번 분위기 가져오기
    public List<String> getSecondMood();
}
