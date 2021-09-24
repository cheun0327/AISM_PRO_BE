package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.Oauth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OauthRepository extends JpaRepository<Oauth, String> {
    List<Oauth> findByPlatformAndEmail(String platform, String email);
}
