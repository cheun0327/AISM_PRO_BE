package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.PlaylistLike;
import com.upvote.aismpro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistLikeRepository extends JpaRepository<PlaylistLike, Long> {
    public List<PlaylistLike> findAllByUser(User user);
}
