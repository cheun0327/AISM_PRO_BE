package com.upvote.aismpro.customrepository;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.entity.QSongDetail;
import com.upvote.aismpro.entity.SongDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SongDetailRepositoryImpl implements SongDetailRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private QSongDetail songDetail = QSongDetail.songDetail;

    public List<String> findSongIdBySearchParamQD(Integer type, Integer length, List<String> genre, List<String> mood1, List<String> mood2) {
        // QSongDetail songDetail = QSongDetail.songDetail;
        System.out.println("string length " + length);
        return queryFactory.select(songDetail.songId)
                .from(songDetail)
                .where(
                        typeEq(type),
                        lengthEq(length)
                                .or(genreIn(genre))
                                .or(mood1In(mood1))
                                .or(mood2In(mood2))
                )
                .fetch();
    }

    // BooleanExpression : 모든 조건이 null 일때 모든 데이터를 불러올 수 있으므로 조심!!
    private BooleanExpression typeEq(Integer type) {
        System.out.println("type 체크");
        System.out.println((type == null) ? "null임" : "null 아님");
        return type == null ? null : songDetail.type.eq(type);
    }

    private BooleanExpression lengthEq(Integer length) {
        System.out.println("length 체크");
        System.out.println((length == null) ? "null임" : "null 아님");
        return length == null ? null : songDetail.length.eq(length);
    }

    private BooleanExpression genreIn(List<String> genre) {
        /*
        System.out.println("genre 체크" + genre);
        System.out.println(CollectionUtils.isEmpty(genre));
        System.out.println(genre.size());
        for(int i = 0; i<genre.size(); i++) {
            System.out.println("genre["+i+"]의 값. " + genre.get(i));
            //리스트 안에 값에 대한 null 체크
            if(genre.get(i) == "") {
                System.out.println("genre["+i+"]의 값은 null 입니다. ");
            }
        }
        */
        // TODO
        // controller에서 받은 값이 공백("")일때, split하면 List가 [공백]으로 저장됨.
        // 값을 받을때 post로 받든 공백 예외처리를 해주든 해야 함.
        return CollectionUtils.isEmpty(genre) ? null : songDetail.genre.in(genre);
    }

    private BooleanExpression mood1In(List<String> mood1) {
        return mood1.isEmpty() ? null : songDetail.mood1.in(mood1);
    }
    private BooleanExpression mood2In(List<String> mood2) {
        return mood2.isEmpty() ? null : songDetail.mood2.in(mood2);
    }

}
