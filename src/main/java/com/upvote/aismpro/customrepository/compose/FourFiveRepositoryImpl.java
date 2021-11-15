package com.upvote.aismpro.customrepository.compose;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.entity.FourFive;
import com.upvote.aismpro.entity.QFourFive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FourFiveRepositoryImpl implements FourFiveRepositoryCustom {
    private final JPAQueryFactory query;
    private final QFourFive fourFive = QFourFive.fourFive;

    @Override
    public List<String> findFiveByFourQD(String four) {
        return query.select(fourFive.five)
                .from(fourFive)
                .where(
                        fourFive.four.eq(four)
                )
                .fetch();
    }

    @Override
    public List<String> findFiveQD(){
        return query.selectDistinct(fourFive.five)
                .from(fourFive)
                .fetch();
    }
}
