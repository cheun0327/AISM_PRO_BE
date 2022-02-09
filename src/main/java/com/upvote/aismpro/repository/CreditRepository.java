package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {
    public Credit findCreditByUser_UserId(Long userId);

}
