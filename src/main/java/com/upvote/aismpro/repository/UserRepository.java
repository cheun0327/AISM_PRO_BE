package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public List<User> findAllByPlatformAndEmail(String platform, String email);
    public List<User> findByEmail(String email);
    public List<User> findByNickname(String nickname);
}
