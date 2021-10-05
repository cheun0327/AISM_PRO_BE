package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<PlayList, String> {
}
