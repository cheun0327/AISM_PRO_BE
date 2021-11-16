package com.upvote.aismpro.customrepository.compose;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.entity.QThreeFour;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ThreeFourRepositoryImpl implements ThreeFourRepositoryCustom {
    private final JPAQueryFactory query;
    private final QThreeFour threeFour = QThreeFour.threeFour;

    @Override
    public List<String> findFourByThreeQD(String three) {
        return query.select(threeFour.four)
                .from(threeFour)
                .where(
                        threeFour.three.eq(three)
                )
                .fetch();
    }
    @Override
    public List<String> findFourQD() {
        return query.selectDistinct(threeFour.three)
                .from(threeFour)
                .fetch();
    }
}
