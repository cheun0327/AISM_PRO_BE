package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.compose.FourFiveRepositoryCustom;
import com.upvote.aismpro.entity.FourFive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FourFiveRepository extends JpaRepository<FourFive, String>, FourFiveRepositoryCustom {
}
