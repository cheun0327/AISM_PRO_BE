package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.compose.FourFiveRepositoryCustom;
import com.upvote.aismpro.entity.FourFive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FourFiveRepository extends JpaRepository<FourFive, String>, FourFiveRepositoryCustom {
}
