package com.upvote.aismpro.customrepository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.dto.SimilarSrcDTO;
import com.upvote.aismpro.dto.SongTagDTO;
import com.upvote.aismpro.entity.QSong;
import com.upvote.aismpro.entity.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SongRepositoryImpl implements SongRepositoryCustom{
    private final JPAQueryFactory query;
    private final QSong song = QSong.song;

    // 비슷한 곡 리스트 반환
    @Override
    public List<Song> findSimilarSongQD(Song src) {
        return query.select(song)
                .from(song)
                .where(
                        song.songId.ne(src.getSongId())
                                .and(oneEq(src.getOne())
                                        .or(twoEq(src.getTwo()))
                                        .or(threeEq(src.getThree()))
                                        .or(fourEq(src.getFour()))
                                        .or(fiveEq(src.getFive()))
                                        .or(sixEq(src.getSix())))
                )
                .fetch();
    }

    @Override
    public List<Song> findSimilarSongByTagsQD(SongTagDTO songTagDTO) {
        List<Song> songs = query.select(song)
                .from(song)
                .where(
                        oneEq(songTagDTO.getOne())
                                .and(twoEq(songTagDTO.getTwo()))
                                .or(threeEq(songTagDTO.getThree()))
                                .or(fourEq(songTagDTO.getFour()))
                                .or(fiveEq(songTagDTO.getFive()))
                                .or(fiveEq(songTagDTO.getSix()))
                )
                .fetch();

        if (songs.isEmpty()) {
            System.out.println("비슷한 곡 없음");
            return query.select(song)
                    .from(song)
                    .fetch();
        }

        return songs;
    }

    // 라이브러리 검색 결과 반환
    public List<Song> findSongBySearchParamQD(LibrarySearchDTO librarySearchDTO) {
        if (librarySearchDTO.getSort().equals("업로드 날짜")) return searchOrderByDate(librarySearchDTO);
            // case "좋아요 수" : return searchOrderByLike(newLibrarySearchDTO);
            return search(librarySearchDTO);

    }

    // 라이브러리 검색 결과 업로드 날짜로 정렬 반환
    private List<Song> searchOrderByDate(LibrarySearchDTO librarySearchDTO) {
        return query.select(song)
                .from(song)
                .where(
                        searchWhere(librarySearchDTO)
                )
                .orderBy(song.createDate.desc())
                .fetch();
    }

    // 라이브러리 검색 결과 그냥 반환
    private List<Song> search(LibrarySearchDTO librarySearchDTO) {
        return query.select(song)
                .from(song)
                .where(
                        searchWhere(librarySearchDTO)
                )
                .fetch();
    }

    // 라이브러리 검색 공통 where문
    private BooleanExpression searchWhere(LibrarySearchDTO librarySearchDTO) {
        if (isNull(librarySearchDTO)) {
            return null;
        }
        else {
            switch(librarySearchDTO.getType()) {
                case "모두" : return song.one.in(librarySearchDTO.getGenre())
                        .or(instIn(librarySearchDTO.getInst()))
                        .or(moodIn(librarySearchDTO.getMood()))
                        .or(playtimeIn(librarySearchDTO.getPlaytime()));
                default : return song.type.in(librarySearchDTO.getType())
                        .or(genreIn(librarySearchDTO.getGenre()))
                        .or(instIn(librarySearchDTO.getInst()))
                        .or(moodIn(librarySearchDTO.getMood()))
                        .or(playtimeIn(librarySearchDTO.getPlaytime()));
            }
        }
    }

    // 라이브러리 검색 옵션 모두 null 인지 체크
    private Boolean isNull(LibrarySearchDTO obj) {
        return (obj.getType().equals("모두") && obj.getSort().equals("업로드 날짜") && obj.getGenre().isEmpty() && obj.getInst().isEmpty()
                && obj.getMood().isEmpty() && obj.getPlaytime().isEmpty() && obj.getTempo().isEmpty());
    }

    private BooleanExpression genreIn(List<String> genre) {
        return genre.isEmpty() ?null : song.one.in(genre);
    }
    private BooleanExpression instIn(List<String> inst) {
        return inst.isEmpty() ? null : song.four.in(inst);
    }
    // mood -> newsong::two, three, four
    // playtime -> newsong::playtime
    private BooleanExpression  moodIn(List<String> mood) {
        return mood.isEmpty() ? null : song.two.in(mood).or(song.three.in(mood)).or(song.four.in(mood));
    }
    private BooleanExpression playtimeIn(List<String> playtime) {
        return playtime.isEmpty() ? null : song.playtime.in(playtime);
    }

    private BooleanExpression oneEq(String one) {
        return one == null ? null : song.one.eq(one);
    }
    private BooleanExpression twoEq(String two) {
        return two == null ? null : song.two.eq(two);
    }
    private BooleanExpression threeEq(String three) {
        return three == null ? null : song.three.eq(three);
    }
    private BooleanExpression fourEq(String four) {
        return four == null ? null : song.four.eq(four);
    }
    private BooleanExpression fiveEq(String five) {
        return five == null ? null : song.five.eq(five);
    }
    private BooleanExpression sixEq(String six) {
        return six == null ? null : song.six.eq(six);
    }

}
