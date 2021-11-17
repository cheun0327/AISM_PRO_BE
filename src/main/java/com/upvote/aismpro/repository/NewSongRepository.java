package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.NewSongRepositoryCustom;
import com.upvote.aismpro.entity.NewSong;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewSongRepository extends JpaRepository<NewSong, String>, NewSongRepositoryCustom {
}
