package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.compose.ThreeFourRepositoryCustom;
import com.upvote.aismpro.entity.ThreeFour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreeFourRepository extends JpaRepository<ThreeFour, String >, ThreeFourRepositoryCustom {
    // 빨간줄 뜨지만 실행은 잘 됨.
    @Query("SELECT DISTINCT tf.four FROM ThreeFour tf WHERE CHAR_LENGTH(tf.three) > 5")
    public List<String> findFour();
}
