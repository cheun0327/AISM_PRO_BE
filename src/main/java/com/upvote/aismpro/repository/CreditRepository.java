package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.CreditRepositoryImpl;
import com.upvote.aismpro.customrepository.CreditRespositoryCustom;
import com.upvote.aismpro.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long>, CreditRespositoryCustom {
    public Credit findCreditByUser_UserId(Long userId);

}
