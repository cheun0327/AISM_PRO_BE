package com.upvote.aismpro.customrepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.entity.QCompose;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ComposeRepositoryImpl implements ComposeRepositoryCustom {
    private final JPAQueryFactory query;
    private final QCompose compose = QCompose.compose;

    public List<String> findGenre() {
        return query.select(compose.genre)
                .distinct()
                .from(compose)
                .fetch();
    }

    public List<String> findFirstMood() {
        return query.select(compose.first_mood)
                .distinct()
                .from(compose)
                .fetch();
    }

    public List<String> findSecondMood() {
        return query.select(compose.second_mood)
                .distinct()
                .from(compose)
                .fetch();
    }

    public List<String> findFirstMoodByGenre(String genre) {
        return query.select(compose.first_mood)
                .distinct()
                .from(compose)
                .where(
                        compose.genre.eq(genre)
                )
                .fetch();
    }

    public List<String> findSecondMoodByFirstMood(String genre, String firstMood) {
        return query.select(compose.second_mood)
                .distinct()
                .from(compose)
                .where(
                        compose.genre.eq(genre).and(compose.first_mood.eq(firstMood))
                )
                .fetch();
    }

    public String findSampleSoundByKeywords(String genre, String firstMood, String secondMood){
        return query.select(compose.sample)
                .distinct()
                .from(compose)
                .where(
                        compose.genre.eq(genre).and(compose.first_mood.eq(firstMood)).and(compose.second_mood.eq(secondMood))
                )
                .fetchOne();
    }
}
