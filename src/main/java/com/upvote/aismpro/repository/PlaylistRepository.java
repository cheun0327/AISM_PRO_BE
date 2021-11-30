package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.PlaylistRepositoryCustom;
import com.upvote.aismpro.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long>, PlaylistRepositoryCustom {
}
