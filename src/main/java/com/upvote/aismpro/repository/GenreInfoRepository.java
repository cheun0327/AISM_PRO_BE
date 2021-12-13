package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.GenreInfoRepositoryCustom;
import com.upvote.aismpro.entity.GenreInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreInfoRepository extends JpaRepository<GenreInfo, Long>, GenreInfoRepositoryCustom {
    // 장르별 카테고리 반환
    public GenreInfo findByGenre(String genre);
}
