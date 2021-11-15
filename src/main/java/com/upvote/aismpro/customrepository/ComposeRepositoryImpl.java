package com.upvote.aismpro.customrepository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ComposeRepositoryImpl implements ComposeRepositoryCustom {
    private final JPAQueryFactory query;
    private final QCompose compose = QCompose.compose;

    @Override
    public List<String> findKeyword(String keyword) {
        switch(keyword) {
            case "genre":
                return query.select(compose.genre)
                        .distinct()
                        .from(compose)
                        .fetch();

            case "firstMood":
                return query.select(compose.first_mood)
                        .distinct()
                        .from(compose)
                        .fetch();

            case "secondMood":
                return query.select(compose.second_mood)
                        .distinct()
                        .from(compose)
                        .fetch();

            default:
                return new ArrayList<String>();
        }
    }

    @Override
    public List<String> findFirstMoodByGenre(String genre) {
        return query.select(compose.first_mood)
                .distinct()
                .from(compose)
                .where(
                        genreEq(genre)
                )
                .fetch();
    }

    @Override
    public List<String> findSecondMoodByFirstMood(String genre, String firstMood) {
        return query.select(compose.second_mood)
                .distinct()
                .from(compose)
                .where(
                        genreEq(genre).and(firstMoodEq(firstMood))
                )
                .fetch();
    }

    @Override
    public String findSampleSoundByKeywords(String genre, String firstMood, String secondMood){
        return query.select(compose.sample)
                .distinct()
                .from(compose)
                .where(
                        genreEq(genre).and(firstMoodEq(firstMood)).and(secondMoodEq(secondMood))
                )
                .fetchOne();
    }

    public BooleanExpression genreEq(String genre) {
        return genre != null ? compose.genre.eq(genre) : null;
    }

    public BooleanExpression firstMoodEq(String firstMood) {
        return firstMood != null ? compose.first_mood.eq(firstMood) : null;
    }

    public BooleanExpression secondMoodEq(String secondMood) {
        return secondMood != null ? compose.second_mood.eq(secondMood) : null;
    }
}
