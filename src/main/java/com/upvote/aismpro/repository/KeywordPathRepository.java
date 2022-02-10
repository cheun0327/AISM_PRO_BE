package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.KeywordPathRepositoryCustom;
import com.upvote.aismpro.entity.KeywordPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordPathRepository extends JpaRepository<KeywordPath, Long>, KeywordPathRepositoryCustom {
}
