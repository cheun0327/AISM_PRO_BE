package com.upvote.aismpro.repository;

import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.entity.PlaylistLike;
import com.upvote.aismpro.entity.User;
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

    // 유저별 플레이리스트 좋아요 목록 가져오기
    public List<PlaylistLike> findAllByUser(User user);
}
