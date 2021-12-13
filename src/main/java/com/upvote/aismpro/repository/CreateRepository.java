package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.CreateRepositoryCustom;
import com.upvote.aismpro.entity.Create;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreateRepository extends JpaRepository<Create, Long>, CreateRepositoryCustom {
    public List<Create> findAllByUser_UserId(Long userId);
    public void deleteByUser_UserIdAndSong_SongId(Long userId, Long songId);
}
