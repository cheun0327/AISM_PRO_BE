package com.upvote.aismpro.customrepository;

import com.upvote.aismpro.entity.KeywordPath;

import java.util.List;

public interface KeywordPathRepositoryCustom {
    List<String> find1stOptions(String genre);

    List<String> find2ndOptions(String genre, String category);

    List<String> find3rdOptions(String genre, String category, String subCategory);

    List<KeywordPath> find4thOptions(String genre, String category, String subCategory, String keyword);

    // 뉴에이지 장르에서 악기 갖오기
    List<String> findInstFromNewageQD();

    // 뉴에이지 장르에서 분위기 가져오기
    List<String> findMoodFromNewageQD();

    // 뉴에이지 아닌 장르에서 분위기 가져오기
    List<String> findMoodFromNotNewageQD();
}
