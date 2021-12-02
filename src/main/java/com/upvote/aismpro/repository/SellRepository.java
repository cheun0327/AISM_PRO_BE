package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.Sell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellRepository extends JpaRepository<Sell, Long> {
    public List<Sell> findAllByUser_UserId(Long userId);
}
