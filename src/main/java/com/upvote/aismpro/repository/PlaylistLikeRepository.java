package com.upvote.aismpro.repository;

import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.entity.PlaylistLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistLikeRepository extends JpaRepository<PlaylistLike, String> {
}
