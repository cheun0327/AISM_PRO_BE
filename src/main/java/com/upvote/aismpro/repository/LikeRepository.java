package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, String> {

    // songId로 like 리스트 가져오기
    public List<Like> findBySong_SongId(String songId);
    // songId로 like count 가져오기
    public Integer countBySong_SongId(String songId);
    // like 삭제
    public void deleteById(String likeId);
    // songId랑 userId로 like 가져오기
    public List<Like> findAllByUser(String userId);

}
