package com.upvote.aismpro.customrepository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.entity.QGenreInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreInfoRepositoryImpl implements GenreInfoRepositoryCustom{
    private final JPAQueryFactory query;
    private final QGenreInfo genreInfo = QGenreInfo.genreInfo;

    // 인덱스 정렬된 장르 리스트
    @Override
    public List<String> findGenreQD(){
        List<Tuple> tuples = query.selectDistinct(genreInfo.idx, genreInfo.genre)
                .from(genreInfo)
                .orderBy(genreInfo.idx.asc())
                .fetch();

        List<String> result = new ArrayList<>();
        for (Tuple t : tuples) {
            result.add(t.get(genreInfo.genre));
        }
        return result;
    }

}
