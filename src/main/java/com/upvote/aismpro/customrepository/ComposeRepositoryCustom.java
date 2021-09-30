package com.upvote.aismpro.customrepository;

import java.util.List;

public interface ComposeRepositoryCustom {
    List<String> findGenre();

    List<String> findFirstMood();

    List<String> findSecondMood();

    List<String> findFirstMoodByGenre(String genre);

    List<String> findSecondMoodByFirstMood(String genre, String firstMood);

    String findSampleSoundByKeywords(String genre, String firstMood, String secondMood);
}
