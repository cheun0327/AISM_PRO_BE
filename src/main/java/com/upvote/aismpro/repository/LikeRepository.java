package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.LikeRepositoryCustom;
import com.upvote.aismpro.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long>, LikeRepositoryCustom {
    public Integer countBySong_SongId(Long songId);
    public List<Like> findAllByUser_UserId(Long userId);
    public List<Like> findAllByUser_UserIdAndSong_SongId(Long userId, Long songId);
    public void deleteByUser_UserIdAndSong_SongId(Long userId, Long songId);
}
