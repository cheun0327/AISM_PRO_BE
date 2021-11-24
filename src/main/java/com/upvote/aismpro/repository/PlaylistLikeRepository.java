package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.PlaylistLikeRepositoryCustom;
import com.upvote.aismpro.customrepository.PlaylistRepositoryCustom;
import com.upvote.aismpro.entity.PlaylistLike;
import com.upvote.aismpro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistLikeRepository extends JpaRepository<PlaylistLike, Long>, PlaylistLikeRepositoryCustom {
    public List<PlaylistLike> findAllByUser(User user);

    @Query("SELECT COUNT(pl) from PlaylistLike pl where pl.playlist.playlistId = :playlistId")
    Integer countPlaylistLikeByID(@Param("playlistId") Long playlistId);
}
