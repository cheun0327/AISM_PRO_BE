package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.SongDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComposeRepository extends JpaRepository<SongDetail, String> {
    @Query("select distinct compose.first_mood from Compose compose where compose.genre = :genre")
    List<String> findFirstMoodByGenre(@Param("genre") String genre);

    @Query("select distinct compose.second_mood from Compose compose where compose.genre = :genre and compose.first_mood = :firstMood")
    List<String> findSecondMoodByFirstMood(@Param("genre") String genre, @Param("firstMood") String firstMood);

    @Query("select distinct compose.sample from Compose compose where compose.genre = :genre and compose.first_mood = :firstMood and compose.second_mood = :secondMood")
    String findSampleSoundByKeywords(@Param("genre") String genre, @Param("firstMood") String firstMood, @Param("secondMood") String secondMood);
}
