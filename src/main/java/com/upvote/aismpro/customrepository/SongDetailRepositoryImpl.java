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
        return queryFactory.select(songDetail.songId)
                .from(songDetail)
                .where(
                        // type and length or genre or mood1 or mood2 or
                        typeIn(librarySearchDTO.getType())
                )
                .where(
                        queryWrapper(librarySearchDTO)
                )
                .fetch();
    }

    public List<SongDetail> findSongDetailBySearchParamQD(LibrarySearchDTO librarySearchDTO) {
        return queryFactory.select(songDetail)
                .from(songDetail)
                .where(
                        typeIn(librarySearchDTO.getType())
                )
                .where(
                        queryWrapper(librarySearchDTO)
                )
                .fetch();
    }

    // BooleanExpression : 모든 조건이 null 일때 모든 데이터를 불러올 수 있으므로 조심!!
    private BooleanExpression typeIn(List<String> type) {
        return type.isEmpty() ? null : songDetail.type.in(type);
    }

    private BooleanExpression queryWrapper(LibrarySearchDTO librarySearchDTO){
        if (librarySearchDTO.getLength().isEmpty() && librarySearchDTO.getGenre().isEmpty()
                && librarySearchDTO.getFirst_Mood().isEmpty() && librarySearchDTO.getSecond_Mood().isEmpty()){
            return null;
        }
        else {
            return songDetail.length.in(librarySearchDTO.getLength())
                    .or(genreIn(librarySearchDTO.getGenre()))
                    .or(mood1In(librarySearchDTO.getFirst_Mood()))
                    .or(mood2In(librarySearchDTO.getSecond_Mood()));
        }
    }

    private BooleanExpression lengthIn(List<String> length) {
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
