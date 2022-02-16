package com.upvote.aismpro.customrepository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.entity.DummyKeywordPath;
import com.upvote.aismpro.entity.KeywordPath;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

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
                        keywordPath.subCategory.hashTag,
                        keywordPath.keyword
                )
                .from(keywordPath)
                .innerJoin(keywordPath.subCategory)
                .fetch();

        List<String> categoryList = tupleList.stream()
                .map(tuple -> tuple.get(keywordPath.category))
                .collect(Collectors.toList());

        List<String> hashTagList = tupleList.stream()
                .map(tuple -> Objects.requireNonNull(tuple.get(keywordPath.subCategory.hashTag)).split("/"))
                .flatMap(Arrays::stream)
                .filter(tag -> tag.indexOf(",") == tag.lastIndexOf(",")) // 콤마(",")가 2개 이상인 문자열 제외
                .collect(Collectors.toList());

        List<String> keywordList = tupleList.stream()
                .map(tuple -> tuple.get(keywordPath.keyword))
                .collect(Collectors.toList());

        Set<String> moodSet = new HashSet<>();
        moodSet.addAll(categoryList);
        moodSet.addAll(hashTagList);
        moodSet.addAll(keywordList);

        moodSet.remove("");

        return new ArrayList<>(moodSet);
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

    @Override
    public KeywordPath findOnePath(
            String genre,
            String category,
            String subCategory,
            String keyword,
            String fx
    ) {
        return queryFactory
                .selectFrom(keywordPath)
                .innerJoin(keywordPath.subCategory)
                .innerJoin(keywordPath.fx)
                .where(
                        keywordPath.genre.eq(genre),
                        keywordPath.category.eq(category),
                        keywordPath.subCategory.keyword.eq(subCategory),
                        keywordPath.keyword.eq(keyword),
                        keywordPath.fx.keyword.eq(fx)
                ).fetchOne();
    }
}
