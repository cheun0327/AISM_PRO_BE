package com.upvote.aismpro.customrepository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.entity.DummyKeywordPath;
import com.upvote.aismpro.entity.KeywordPath;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.upvote.aismpro.entity.QDummyKeywordPath.dummyKeywordPath;
import static com.upvote.aismpro.entity.QKeywordPath.keywordPath;

@RequiredArgsConstructor
@Repository
public class KeywordPathRepositoryImpl implements KeywordPathRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> find1stOptions(String genre) {
        return queryFactory
                .selectDistinct(keywordPath.category)
                .from(keywordPath)
                .where(keywordPath.genre.eq(genre))
                .fetch();
    }

    @Override
    public List<String> find2ndOptions(String genre, String category) {
        return queryFactory
                .selectDistinct(keywordPath.subCategory.keyword)
                .from(keywordPath)
                .innerJoin(keywordPath.subCategory)
                .where(
                        keywordPath.genre.eq(genre),
                        keywordPath.category.eq(category)
                )
                .fetch();
    }

    @Override
    public List<String> find3rdOptions(String genre, String category, String subCategory) {
        return queryFactory
                .selectDistinct(keywordPath.keyword)
                .from(keywordPath)
                .innerJoin(keywordPath.subCategory)
                .where(
                        keywordPath.genre.eq(genre),
                        keywordPath.category.eq(category),
                        keywordPath.subCategory.keyword.eq(subCategory)
                ).fetch();
    }

    @Override
    public List<KeywordPath> find4thOptions(String genre, String category, String subCategory, String keyword) {
        return queryFactory
                .selectFrom(keywordPath)
                .innerJoin(keywordPath.subCategory)
                .innerJoin(keywordPath.fx)
                .where(
                        keywordPath.genre.eq(genre),
                        keywordPath.category.eq(category),
                        keywordPath.subCategory.keyword.eq(subCategory),
                        keywordPath.keyword.eq(keyword)
                ).fetch();
    }

    @Override
    public List<String> findInstFromNewageQD() {
        return queryFactory
                .selectDistinct(keywordPath.keyword)
                .from(keywordPath)
                .where(keywordPath.genre.eq("Newage"))
                .fetch();
    }

    @Override
    public List<String> findMoodFromNewageQD() {
        List<Tuple> tupleList = queryFactory
                .selectDistinct(
                        keywordPath.category,
                        keywordPath.subCategory.keyword,
                        keywordPath.keyword
                )
                .from(keywordPath)
                .innerJoin(keywordPath.subCategory)
                .fetch();

        Set<String> keywordSet = new HashSet<>();

        tupleList.forEach(tuple -> {
            keywordSet.add(tuple.get(keywordPath.category));
            keywordSet.add(tuple.get(keywordPath.subCategory.keyword));
            keywordSet.add(tuple.get(keywordPath.keyword));
        });

        return new ArrayList<>(keywordSet);
    }

    @Override
    public List<String> findMoodFromNotNewageQD() {
        List<DummyKeywordPath> tuples = queryFactory
                .selectFrom(dummyKeywordPath)
                .fetch();

        Set<String> keywordSet = new HashSet<>();

        for (DummyKeywordPath tuple : tuples) {
            keywordSet.add(tuple.getMood1());
            keywordSet.add(tuple.getMood2());
            keywordSet.add(tuple.getMood3());
        }

        return new ArrayList<>(keywordSet);
    }
}
