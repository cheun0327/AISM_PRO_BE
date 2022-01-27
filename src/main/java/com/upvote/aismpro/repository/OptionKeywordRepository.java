package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.OptionKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionKeywordRepository extends JpaRepository<OptionKeyword, Long> {

    List<OptionKeyword> findByKeywordNameIn(List<String> keywordNames);
}
