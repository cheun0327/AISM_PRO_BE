package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.OptionKeywordNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionKeywordNodeRepository extends JpaRepository<OptionKeywordNode, Long> {

    @Query("SELECT okn " +
            "FROM OptionKeywordNode okn " +
            "INNER JOIN fetch okn.keyword " +
            "INNER JOIN fetch okn.genre " +
            "WHERE okn.genre.genreName = :genreName " +
            "AND okn.depth = 0")
    List<OptionKeywordNode> findRootListByGenreName(@Param("genreName") String genreName);

    @Query("SELECT okn " +
            "FROM OptionKeywordNode okn " +
            "INNER JOIN fetch okn.keyword " +
            "WHERE okn.path like :path% " +
            "AND okn.depth = :depth")
    List<OptionKeywordNode> findChildByPathAndDepth(
            @Param("path") String path,
            @Param("depth") int depth
    );
}
