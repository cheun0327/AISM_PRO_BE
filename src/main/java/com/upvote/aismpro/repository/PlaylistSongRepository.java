package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.PlaylistSongRepositoryCustom;
import com.upvote.aismpro.entity.PlaylistSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistSongRepository extends JpaRepository<PlaylistSong, Long>, PlaylistSongRepositoryCustom {
}
