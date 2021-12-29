package com.upvote.aismpro.customrepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.entity.QKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class KeywordRepositoryImpl implements KeywordRepositoryCustom {
    private final JPAQueryFactory query;
    private final QKeyword keyword = QKeyword.keyword;

    @Override
    public List<String> find2ndQD(String one){
        List<String> twos = query.selectDistinct(keyword.two)
                .from(keyword)
                .where(keyword.one.eq(one))
//                .orderBy(keyword.id.asc())
                .fetch();
        return twos;
    }

    @Override
    public List<String> find3rdQD(String one, String two){
        List<String> threes = query.selectDistinct(keyword.three)
                .from(keyword)
                .where(keyword.one.eq(one).and(keyword.two.eq(two)))
//                .orderBy(keyword.id.asc())
                .fetch();
        return threes;
    }

    @Override
    public List<String> find4thQD(String one, String two, String three){
        List<String> fours = query.selectDistinct(keyword.four)
                .from(keyword)
                .where(
                        keyword.one.eq(one)
                                .and(keyword.two.eq(two))
                                .and(keyword.three.eq(three))
                )
//                .orderBy(keyword.id.asc())
                .fetch();
        return fours;
    }

    @Override
    public List<String> find5thQD(String one, String two, String three, String four){
        List<String> fives = query.select(keyword.five)
                .from(keyword)
                .where(
                        keyword.one.eq(one)
                                .and(keyword.two.eq(two))
                                .and(keyword.three.eq(three))
                                .and(keyword.four.eq(four))
                )
                .orderBy(keyword.id.asc())
                .fetch();
        return fives.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<String> find6thQD(String one, String two, String three, String four, String five){
        List<String> sixs = query.selectDistinct(keyword.six)
                .from(keyword)
                .where(
                        keyword.one.eq(one)
                                .and(keyword.two.eq(two))
                                .and(keyword.three.eq(three))
                                .and(keyword.four.eq(four))
                                .and(keyword.five.eq(five))
                )
//                .orderBy(keyword.id.asc())
                .fetch();
        return sixs;
    }

    @Override
    public List<String> findInstFromNewageQD() {
        List<String> inst = query.selectDistinct(keyword.four)
                .from(keyword)
                .where(
                        keyword.one.eq("Newage")
                )
                .fetch();
        return inst;
    }

    @Override
    public List<String> findMoodFromNewageQD() {
        List<String> mood = query.selectDistinct(keyword.three)
                .from(keyword)
                .where(
                        keyword.one.eq("Newage")
                )
                .fetch();
        return mood;
    }

    @Override
    public List<String> findMoodFromNotNewageQD() {
        List<Tuple> tuples = query.selectDistinct(keyword.two, keyword.three, keyword.four)
                .from(keyword)
                .where(
                        keyword.one.ne("Newage")
                )
                .fetch();

        List<String> mood = new ArrayList<>();
        for (Tuple t : tuples) {
            mood.add(t.get(keyword.two));
            mood.add(t.get(keyword.three));
            mood.add(t.get(keyword.four));
        }
        return mood.stream().distinct().collect(Collectors.toList());
    }

}
