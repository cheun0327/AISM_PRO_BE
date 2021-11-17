package com.upvote.aismpro.customrepository;

import java.util.List;

public interface ComposeRepositoryCustom {

    List<String> findKeyword(String keyword);

    List<String> findFirstMoodByGenre(String genre);

    List<String> findSecondMoodByFirstMood(String genre, String firstMood);

    String findSampleSoundByKeywords(String genre, String firstMood, String secondMood);
}
