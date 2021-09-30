package com.upvote.aismpro.customrepository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.entity.QSongDetail;
import com.upvote.aismpro.entity.SongDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SongDetailRepositoryImpl implements SongDetailRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public List<String> findGenre() {
        QSongDetail songDetail = QSongDetail.songDetail;

        return queryFactory.select(songDetail.genre)
                .distinct()
                .from(songDetail)
                .fetch();
    }

    public List<String> findFirstMood() {
        QSongDetail songDetail = QSongDetail.songDetail;

        return queryFactory.select(songDetail.mood1)
                .distinct()
                .from(songDetail)
                .fetch();
    }

    public List<String> findSecondMood() {
        QSongDetail songDetail = QSongDetail.songDetail;

        return queryFactory.select(songDetail.mood2)
                .distinct()
                .from(songDetail)
                .fetch();
    }

    public List<String> findSongIdBySearchParamQD(String type, String length, List<String> genre, List<String> mood1, List<String> mood2) {
        QSongDetail songDetail = QSongDetail.songDetail;

        return queryFactory.select(songDetail.songId)
                .from(songDetail)
                .where(
                        songDetail.type.eq(type).or(songDetail.length.eq(Integer.valueOf(length))),

                        songDetail.genre.in(genre),
                        songDetail.mood1.in(mood1),
                        songDetail.mood2.in(mood2)
                )
                .fetch();
    }
//    public List<SongDetail> findBySearchParamQD(String type, String length, String genre, String mood1, String mood2) {
//        QSongDetail songDetail = QSongDetail.songDetail;
//
//        return queryFactory.selectFrom(songDetail)
//                .where(
//                        songDetail.genre.in(genre)
//                )
//                .fetch();
//    }\

}
