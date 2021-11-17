package com.upvote.aismpro.customrepository.compose;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.customrepository.compose.TwoThreeRepositoryCustom;
import com.upvote.aismpro.entity.QTwoThree;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TwoThreeRepositoryImpl implements TwoThreeRepositoryCustom {
    private final JPAQueryFactory query;
    private final QTwoThree twoThree = QTwoThree.twoThree;

    @Override
    public List<String> findThreeByTwoQD(String two) {
        return query.select(twoThree.three)
                .from(twoThree)
                .where(
                        twoThree.two.eq(two)
                )
                .fetch();
    }

    @Override
    public List<String> findThreeQD() {
        return query.selectDistinct(twoThree.three)
                .from(twoThree)
                .fetch();
    }
}
