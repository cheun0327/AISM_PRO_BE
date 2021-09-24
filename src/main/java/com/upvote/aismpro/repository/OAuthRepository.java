package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.OAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OAuthRepository extends JpaRepository<OAuth, String> {
    List<OAuth> findByPlatformAndEmail(String platform, String email);
}
