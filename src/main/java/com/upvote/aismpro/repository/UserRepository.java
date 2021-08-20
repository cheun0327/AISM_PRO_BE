package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Member, String> {

    public Optional<Member> findByUserId(String userId);

    public List<Member> findByUserName(String userName);

    public List<Member> findByUserNameLike(String keyword);
}
