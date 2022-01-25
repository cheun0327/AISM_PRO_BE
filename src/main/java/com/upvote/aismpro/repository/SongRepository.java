package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.SongRepositoryCustom;
import com.upvote.aismpro.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long>, SongRepositoryCustom {
    public Optional<Song> findBySongId(Long songId);

    @Modifying
    @Query("UPDATE Song s " +
            "SET s.deletedDate = :now " +
            "WHERE s.songId IN(:songIdList)")
    void deleteAllInSongIdList(
            @Param("now") LocalDateTime now,
            @Param("songIdList") List<Long> songIdList
    );
}
