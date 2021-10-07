package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.ComposeRepositoryCustom;
import com.upvote.aismpro.entity.Compose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComposeRepository extends JpaRepository<Compose, String>, ComposeRepositoryCustom {
}
