package com.upvote.aismpro.customrepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.entity.QKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class KeywordRepositoryImpl implements KeywordRepositoryCustom {
    private final JPAQueryFactory query;
    private final QKeyword keyword = QKeyword.keyword;

//    public List<String> findOneQD(){
//        List<Tuple> tuple = query.selectDistinct(oneTwo)
//    }
}
