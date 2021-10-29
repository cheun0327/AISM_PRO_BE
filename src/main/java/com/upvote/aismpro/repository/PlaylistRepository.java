package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.PlaylistRepositoryCustom;
import com.upvote.aismpro.entity.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PlaylistRepository extends JpaRepository<PlayList, String>, PlaylistRepositoryCustom {
    List<PlayList> findByPlaylistIdNotAndFirstMoodOrSecondMoodOrThirdMood(String playlistID, String firstMood, String secondMood, String thirdMood);
}
