package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.compose.TwoThreeRepositoryCustom;
import com.upvote.aismpro.entity.TwoThree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TwoThreeRepository extends JpaRepository<TwoThree, String>, TwoThreeRepositoryCustom {
}
