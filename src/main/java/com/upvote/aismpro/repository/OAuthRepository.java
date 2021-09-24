package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.OAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OAuthRepository extends JpaRepository<OAuth, String> {
    Optional<OAuth> findByPlatformAndEmail(String platform, String email);
}
