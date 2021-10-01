package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.SongRepositoryCustom;
import com.upvote.aismpro.customrepository.SongRepositoryQD;
import com.upvote.aismpro.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, String>, SongRepositoryCustom {
}
