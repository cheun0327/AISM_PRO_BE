package com.upvote.aismpro.repository;

//import com.upvote.aismpro.customrepository.ComposeRepositoryCustom;
import com.upvote.aismpro.customrepository.compose.OneTwoRepositoryCustom;
import com.upvote.aismpro.entity.OneTwo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OneTwoRepository extends JpaRepository<OneTwo, String>,OneTwoRepositoryCustom {

    public List<OneTwo> findAll();

    // 빨간줄 뜨지만 실행은 잘 됨.
    @Query("SELECT DISTINCT ot.two FROM OneTwo ot WHERE CHAR_LENGTH(ot.two) > 2")
    public List<String> findTwo();
}
