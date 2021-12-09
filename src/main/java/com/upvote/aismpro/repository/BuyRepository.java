package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.BuyRepositoryCustom;
import com.upvote.aismpro.entity.Buy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyRepository extends JpaRepository<Buy, Long>, BuyRepositoryCustom {
    public List<Buy> findAllByUser_UserId(Long userId);
    public void deleteByUser_UserIdAndSong_SongId(Long userId, Long songId);
}
