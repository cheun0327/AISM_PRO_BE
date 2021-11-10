package com.upvote.aismpro.repository;

import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.entity.PlaylistLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PlaylistLikeRepository extends JpaRepository<PlaylistLike, String> {
    @Query("SELECT COUNT(pl) from PlaylistLike pl where pl.playlist.playlistId = :playlistID")
    Integer countPlaylistLikeByID(@Param("playlistID") String playlistID);
}
