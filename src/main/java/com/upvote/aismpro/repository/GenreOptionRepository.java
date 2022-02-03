package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.GenreOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreOptionRepository extends JpaRepository<GenreOption, Long> {

    List<GenreOption> findByOptionNameIn(List<String> nameList);
}
