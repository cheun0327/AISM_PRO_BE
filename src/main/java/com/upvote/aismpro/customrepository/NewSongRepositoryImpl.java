package com.upvote.aismpro.customrepository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.dto.NewLibrarySearchDTO;
import com.upvote.aismpro.entity.NewSong;
import com.upvote.aismpro.entity.QNewSong;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NewSongRepositoryImpl implements NewSongRepositoryCustom{
    private final JPAQueryFactory query;
    private final QNewSong newSong = QNewSong.newSong;

    @Override
    public List<NewSong> findSongBySearchParamQD(NewLibrarySearchDTO newLibrarySearchDTO) {
        // 정렬별로 나눔 업로드 날짜, 관련성, 재생 수, 좋아요 수, 다운로드 수
        // type -> newsong::type
        // genre -> newsong::one
        // inst -> newsong::four
        // mood -> newsong::two, three, four
        // playtime -> newsong::playtime
        // tempo -> x
        switch (newLibrarySearchDTO.getSort()) {
            case "업로드 날짜" : return searchOrderByDate(newLibrarySearchDTO);
            // case "좋아요 수" : return searchOrderByLike(newLibrarySearchDTO);
            default : return search(newLibrarySearchDTO);
        }
    }

    private List<NewSong> searchOrderByDate(NewLibrarySearchDTO newLibrarySearchDTO) {
        return query.select(newSong)
                .from(newSong)
                .where(
                        searchWhere(newLibrarySearchDTO)
                )
//                .orderBy(newSong.createDate.asc())
                .fetch();
    }

    private List<NewSong> searchOrderByLike(NewLibrarySearchDTO newLibrarySearchDTO) {
        return query.select(newSong)
                .from(newSong)
                .where(
                        searchWhere(newLibrarySearchDTO)
                )
                .fetch();
    }

    private List<NewSong> search(NewLibrarySearchDTO newLibrarySearchDTO) {
        return query.select(newSong)
                .from(newSong)
                .where(
                        searchWhere(newLibrarySearchDTO)
                )
                .fetch();
    }

    private BooleanExpression searchWhere(NewLibrarySearchDTO newLibrarySearchDTO) {
        if (isNull(newLibrarySearchDTO)) {
            return null;
        }
        else {
            switch(newLibrarySearchDTO.getType()) {
                case "모두" : return newSong.one.in(newLibrarySearchDTO.getGenre())
                        .and(instIn(newLibrarySearchDTO.getInst()))
                        .and(moodIn(newLibrarySearchDTO.getMood()))
                        .and(playtimeIn(newLibrarySearchDTO.getPlaytime()));
                default : return newSong.type.in(newLibrarySearchDTO.getType())
                        .and(genreIn(newLibrarySearchDTO.getGenre()))
                        .and(instIn(newLibrarySearchDTO.getInst()))
                        .and(moodIn(newLibrarySearchDTO.getMood()))
                        .and(playtimeIn(newLibrarySearchDTO.getPlaytime()));
            }
        }
    }

    private Boolean isNull(NewLibrarySearchDTO obj) {
        return (obj.getType().equals("모두") && obj.getSort().equals("업로드 날짜") && obj.getGenre().isEmpty() && obj.getInst().isEmpty()
                && obj.getMood().isEmpty() && obj.getPlaytime().isEmpty() && obj.getTempo().isEmpty());
    }

    private BooleanExpression genreIn(List<String> genre) {
        return genre.isEmpty() ?null : newSong.one.in(genre);
    }
    private BooleanExpression instIn(List<String> inst) {
        return inst.isEmpty() ? null : newSong.four.in(inst);
    }
    // mood -> newsong::two, three, four
    // playtime -> newsong::playtime
    private BooleanExpression  moodIn(List<String> mood) {
        return mood.isEmpty() ? null : newSong.two.in(mood).or(newSong.three.in(mood)).or(newSong.four.in(mood));
    }
    private BooleanExpression playtimeIn(List<String> playtime) {
        return playtime.isEmpty() ? null : newSong.playtime.in(playtime);
    }
}
