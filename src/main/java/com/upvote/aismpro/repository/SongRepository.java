package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.SongRepositoryCustom;
import com.upvote.aismpro.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long>, SongRepositoryCustom {
    public Optional<Song> findBySongId(Long songId);
}
