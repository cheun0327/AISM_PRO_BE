package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComposeRepository extends JpaRepository<User, String> {
    @Query("select distinct compose.genre from Compose compose")
    List<String> findAllGenre();

    @Query("select distinct compose.first_mood from Compose compose")
    List<String> findAllFirstMood();

    @Query("select distinct compose.second_mood from Compose compose")
    List<String> findAllSecondMood();
}
