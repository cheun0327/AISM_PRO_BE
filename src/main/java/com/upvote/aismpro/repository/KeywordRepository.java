package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.KeywordRepositoryCustom;
import com.upvote.aismpro.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long>, KeywordRepositoryCustom {
}
