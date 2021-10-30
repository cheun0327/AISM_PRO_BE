package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.PlaylistSongRepositoryCustom;
import com.upvote.aismpro.entity.PlayListSong;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistSongRepository extends JpaRepository<PlayListSong, String>, PlaylistSongRepositoryCustom {
}
