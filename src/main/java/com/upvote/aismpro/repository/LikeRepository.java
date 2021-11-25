package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    public Integer countBySong_SongId(Long songId);
}
