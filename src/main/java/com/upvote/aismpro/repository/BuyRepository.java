package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.Buy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyRepository extends JpaRepository<Buy, Long> {
    public List<Buy> findAllByUser_UserId(Long userId);
}
