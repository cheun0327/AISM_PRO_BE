package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.OptionKeywordNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OptionKeywordNodeRepository extends JpaRepository<OptionKeywordNode, Long> {

    @Query("SELECT qkn " +
            "FROM OptionKeywordNode qkn " +
            "INNER JOIN fetch qkn.keyword " +
            "WHERE qkn.keyword.keywordName = :keywordName " +
            "AND qkn.genre.genreId = :genreId")
    Optional<OptionKeywordNode> findByNameFetchKeyword(
            @Param("keywordName") String keywordName,
            @Param("genreId") Long genreId
    );
}
