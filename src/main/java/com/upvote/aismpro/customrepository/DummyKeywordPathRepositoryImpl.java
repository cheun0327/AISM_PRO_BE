package com.upvote.aismpro.customrepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.upvote.aismpro.entity.QDummyKeywordPath.dummyKeywordPath;

@RequiredArgsConstructor
@Repository
public class DummyKeywordPathRepositoryImpl implements DummyKeywordPathRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> find1stDummyOptions() {
        return queryFactory
                .selectDistinct(dummyKeywordPath.mood1)
                .from(dummyKeywordPath)
                .fetch();
    }

    @Override
    public List<String> find2ndDummyOptions(String mood1) {
        return queryFactory
                .selectDistinct(dummyKeywordPath.mood2)
                .from(dummyKeywordPath)
                .where(dummyKeywordPath.mood1.eq(mood1))
                .fetch();
    }

    @Override
    public List<String> find3rdDummyOptions(String mood1, String mood2) {
        return queryFactory
                .selectDistinct(dummyKeywordPath.mood3)
                .from(dummyKeywordPath)
                .where(
                        dummyKeywordPath.mood1.eq(mood1),
                        dummyKeywordPath.mood2.eq(mood2)
                ).fetch();
    }
}
