package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.BookEntity;
import com.upvote.aismpro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByEmail(String email);
    List<User> findByNickName(String nickName);}
