package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.BookEntity;
import com.upvote.aismpro.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
