package com.upvote.aismpro.customrepository;


import com.querydsl.core.types.CollectionExpression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.entity.QSongDetail;
import com.upvote.aismpro.entity.SongDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SongDetailRepositoryImpl implements SongDetailRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private QSongDetail songDetail = QSongDetail.songDetail;

    public List<String> findSongIdBySearchParamQD(LibrarySearchDTO librarySearchDTO) {
        // QSongDetail songDetail = QSongDetail.songDetail;
        return queryFactory.select(songDetail.songId)
                .from(songDetail)
                .where(
                        typeIn(librarySearchDTO.getType()),
                        lengthIn(librarySearchDTO.getLength())
                                .or(genreIn(librarySearchDTO.getGenre()))
                                .or(mood1In(librarySearchDTO.getMood1()))
                                .or(mood2In(librarySearchDTO.getMood2()))
                )
                .fetch();
    }

    // BooleanExpression : 모든 조건이 null 일때 모든 데이터를 불러올 수 있으므로 조심!!
    private BooleanExpression typeIn(List<String> type) {
        if (type.contains("song")){
            System.out.println("song 포함!");
        }
        return type.isEmpty() ? null : songDetail.type.in(type);
    }

    private BooleanExpression lengthIn(List<String> length) {
        //System.out.println(length.isEmpty()?"null 맞음":length.stream().map(Integer::parseInt).collect(Collectors.toList()));
        return length.isEmpty() ? null : songDetail.length.in(length);
    }

    private BooleanExpression genreIn(List<String> genre) {
        return genre.isEmpty() ? null : songDetail.genre.in(genre);
    }

    private BooleanExpression mood1In(List<String> mood1) {
        return mood1.isEmpty() ? null : songDetail.mood1.in(mood1);
    }
    private BooleanExpression mood2In(List<String> mood2) {
        return mood2.isEmpty() ? null : songDetail.mood2.in(mood2);
    }

}
