package com.upvote.aismpro.customrepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.entity.QCompose;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ComposeRepositoryImpl implements ComposeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public List<String> findGenre() {
        QCompose compose = QCompose.compose;

        return queryFactory.select(compose.genre)
                .distinct()
                .from(compose)
                .fetch();
    }

    public List<String> findFirstMood() {
        QCompose compose = QCompose.compose;

        return queryFactory.select(compose.first_mood)
                .distinct()
                .from(compose)
                .fetch();
    }

    public List<String> findSecondMood() {
        QCompose compose = QCompose.compose;

        return queryFactory.select(compose.second_mood)
                .distinct()
                .from(compose)
                .fetch();
    }

    public List<String> findFirstMoodByGenre(String genre) {
        QCompose compose = QCompose.compose;

        return queryFactory.select(compose.first_mood)
                .distinct()
                .from(compose)
                .where(
                        compose.genre.eq(genre)
                )
                .fetch();
    }

    public List<String> findSecondMoodByFirstMood(String genre, String firstMood) {
        QCompose compose = QCompose.compose;

        return queryFactory.select(compose.second_mood)
                .distinct()
                .from(compose)
                .where(
                        compose.genre.eq(genre).and(compose.first_mood.eq(firstMood))
                )
                .fetch();
    }


    public String findSampleSoundByKeywords(String genre, String firstMood, String secondMood){
        QCompose compose = QCompose.compose;

        return queryFactory.select(compose.sample)
                .distinct()
                .from(compose)
                .where(
                        compose.genre.eq(genre).and(compose.first_mood.eq(firstMood)).and(compose.second_mood.eq(secondMood))
                )
                .fetchOne();
    }
}
