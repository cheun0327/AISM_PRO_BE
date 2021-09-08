package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.BookEntity;
import com.upvote.aismpro.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
