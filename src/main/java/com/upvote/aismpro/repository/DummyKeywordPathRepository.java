package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.DummyKeywordPathRepositoryCustom;
import com.upvote.aismpro.entity.DummyKeywordPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DummyKeywordPathRepository extends JpaRepository<DummyKeywordPath, Long>, DummyKeywordPathRepositoryCustom {
}
