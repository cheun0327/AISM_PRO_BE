package com.upvote.aismpro.customrepository;

import java.util.List;

public interface KeywordRepositoryCustom {
    public List<String> find2ndQD(String one);

    public List<String> find3rdQD(String one, String two);

    public List<String> find4thQD(String one, String two, String three);

    public List<String> find5thQD(String one, String two, String three, String four);

    public List<String> find6thQD(String one, String two, String three, String four, String five);

    // 뉴에이지 장르에서 악기 갖오기
    public List<String> findInstFromNewageQD();

    // 뉴에이지 장르에서 분위기 가져오기
    public List<String> findMoodFromNewageQD();
    // 뉴에이지 아닌 장르에서 분위기 가져오기
    public List<String> findMoodFromNotNewageQD();
}
