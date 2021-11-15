package com.upvote.aismpro.customrepository.compose;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.customrepository.compose.OneTwoRepositoryCustom;
import com.upvote.aismpro.entity.QOneTwo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OneTwoRepositoryImpl implements OneTwoRepositoryCustom {
    private final JPAQueryFactory query;
    private final QOneTwo oneTwo = QOneTwo.oneTwo;

    @Override
    public List<String> findOneQD() {
        List<Tuple> ones = query.selectDistinct(oneTwo.one, oneTwo.indx)
                .from(oneTwo)
                .orderBy(oneTwo.indx.asc())
                .fetch();
        List<String> result = new ArrayList<>();
        for (Tuple t : ones) {
            result.add(t.get(oneTwo.one));
        }
        return result;
    }

    @Override
    public List<String> findTwoQD() {
        return query.selectDistinct(oneTwo.two)
                .from(oneTwo)
                .where(
                        oneTwo.two.length().gt(2)
                )
                .fetch();
    }

    @Override
    public List<String> findTwoByOneQD(String one) {
        return query.select(oneTwo.two)
                .from(oneTwo)
                .where(
                        oneTwo.one.eq(one)
                )
                .fetch();
    }

}
