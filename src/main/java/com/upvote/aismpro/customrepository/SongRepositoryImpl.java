package com.upvote.aismpro.customrepository;

import com.google.api.client.util.Lists;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.dto.SongTagDTO;
import com.upvote.aismpro.entity.QSong;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class SongRepositoryImpl implements SongRepositoryCustom {
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
    @Override
    public Page<Song> findSongBySearchParamQD(LibrarySearchDTO librarySearchDTO) {

        switch (librarySearchDTO.getSort()) {
            case "업로드 날짜":
                return searchOrderByDate(librarySearchDTO);
            // case "좋아요 수" : return searchOrderByLike(newLibrarySearchDTO);
            default:
                return search(librarySearchDTO);
        }
    }

    // 라이브러리 검색 이후 전체보기 페이지
    // TODO Paging 처리 적용하기 -> 무한 스크롤 개발 시점에
    @Override
    public Page<Song> findLibraryTotalSongSearchQD(Pageable pageable, LibrarySearchDTO librarySearchDTO) {
        switch (librarySearchDTO.getSort()) {
            case "업로드 날짜":
                return pagingSearchOrderByDate(pageable, librarySearchDTO);
            // case "좋아요 수" : return searchOrderByLike(newLibrarySearchDTO);
            default:
                return pagingSearch(pageable, librarySearchDTO);
        }
    }

    @Override
    public List<User> findLibraryTotalArtistSearchQD(Pageable pageable, String search) {

        if (!search.equals("") && search != null) {
            System.out.println(pageable);
            QueryResults<User> results = query.selectDistinct(song.user)
                    .from(song)
                    .where(
                            song.user.nickname.contains(search)
                    )
                    .offset(pageable.getOffset())
                    .limit(15)
                    .fetchResults();

            return results.getResults();

        } else {
            System.out.println("결과 없음");
            QueryResults<User> results = query.selectDistinct(song.user)
                    .from(song)
                    .offset(pageable.getOffset())
                    .limit(15)
                    .fetchResults();

            return results.getResults();
        }
    }

    // 유저가 작곡한 곡이 3개 이상인지 확인
    @Override
    public boolean isEnoughAddToPlaylistQD(Long userId) {
        long songCount = query.selectFrom(song)
                .where(song.user.userId.eq(userId))
                .fetchCount();

        return songCount >= 3;
    }

    // 유저가 작곡한 곡 3개 불러오기
    @Override
    public List<Song> findSongListByUserIdLimit3QD(Long userId) {
        return query.selectFrom(song)
                .innerJoin(song.user).fetchJoin()
                .where(userId != null ? song.user.userId.eq(userId) : null)
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(3)
                .fetch();
    }

    // 라이브러리 검색 결과 업로드 날짜로 정렬 반환
    private Page<Song> searchOrderByDate(LibrarySearchDTO librarySearchDTO) {
        QueryResults<Song> results = query.select(song)
                .from(song)
                .where(
                        searchWhere(librarySearchDTO)
                )
                .orderBy(song.createDate.desc())
                .offset(0)
                .limit(6)
                .fetchResults();

        return new PageImpl<>(results.getResults());
    }

    // 라이브러리 검색 결과 그냥 반환
    private Page<Song> search(LibrarySearchDTO librarySearchDTO) {
        QueryResults<Song> results = query.select(song)
                .from(song)
                .where(
                        searchWhere(librarySearchDTO)
                )
                .offset(0)
                .limit(6)
                .fetchResults();

        return new PageImpl<>(results.getResults());
    }

    //// ㄹㅏ이브러리 검색 전체보기
    private Page<Song> pagingSearchOrderByDate(Pageable pageable, LibrarySearchDTO librarySearchDTO) {
        QueryResults<Song> results = query.select(song)
                .from(song)
                .where(
                        searchWhere(librarySearchDTO)
                )
                .orderBy(song.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults());
    }

    private Page<Song> pagingSearch(Pageable pageable, LibrarySearchDTO librarySearchDTO) {
        QueryResults<Song> results = query.select(song)
                .from(song)
                .where(
                        searchWhere(librarySearchDTO)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults());
    }

    // 아티스트
    public List<User> findLibraryArtistSearchQD(String search) {
        if (!Objects.equals(search, "") && search != null) {
            List<User> results = query.selectDistinct(song.user)
                    .from(song)
                    .fetch();

            Collections.shuffle(results);

            return Lists.newArrayList(results.subList(0, 9));
        } else {
            QueryResults<User> results = query.selectDistinct(song.user)
                    .from(song)
                    .where(
                            song.user.nickname.contains(search)
                    )
                    .offset(0)
                    .limit(10)
                    .fetchResults();

            return results.getResults();
        }
    }

    // 라이브러리 검색 공통 where문
    private BooleanExpression searchWhere(LibrarySearchDTO librarySearchDTO) {
        System.out.println("\n\n하이\n\n" + librarySearchDTO.getSearch());
        if (isNull(librarySearchDTO)) {
            return null;
        }

        if (librarySearchDTO.getSearch().equals("") || librarySearchDTO.getSearch() == null) {
            System.out.println("키워드 없음");
            switch (librarySearchDTO.getType()) {
                case "모두": {
                    return song.one.in(librarySearchDTO.getGenre())
                            .or(instIn(librarySearchDTO.getInst()))
                            .or(moodIn(librarySearchDTO.getMood()))
                            .or(playtimeIn(librarySearchDTO.getPlaytime()));
                }
                default: {
                    return song.type.in(librarySearchDTO.getType())
                            .or(genreIn(librarySearchDTO.getGenre()))
                            .or(instIn(librarySearchDTO.getInst()))
                            .or(moodIn(librarySearchDTO.getMood()))
                            .or(playtimeIn(librarySearchDTO.getPlaytime()));
                }
            }
        } else {
            switch (librarySearchDTO.getType()) {
                case "모두": {
                    return song.songName.contains(librarySearchDTO.getSearch())
                            .or(
                                    song.user.nickname.contains(librarySearchDTO.getSearch())
                            )
                            .or(
                                    song.one.contains(librarySearchDTO.getSearch())
                                            .or(song.two.contains(librarySearchDTO.getSearch()))
                                            .or(song.three.contains(librarySearchDTO.getSearch()))
                                            .or(song.four.contains(librarySearchDTO.getSearch()))
                                            .or(song.five.contains(librarySearchDTO.getSearch()))
                                            .or(song.six.contains(librarySearchDTO.getSearch()))
                            )
                            .or(
                                    song.one.in(librarySearchDTO.getGenre())
                                            .or(instIn(librarySearchDTO.getInst()))
                                            .or(moodIn(librarySearchDTO.getMood()))
                                            .or(playtimeIn(librarySearchDTO.getPlaytime()))

                            );
                }
                default: {
                    return song.type.in(librarySearchDTO.getType())
                            .and(
                                    song.songName.contains(librarySearchDTO.getSearch())
                                            .or(
                                                    song.user.nickname.contains(librarySearchDTO.getSearch())
                                            )
                                            .or(
                                                    song.one.contains(librarySearchDTO.getSearch())
                                                            .or(song.two.contains(librarySearchDTO.getSearch()))
                                                            .or(song.three.contains(librarySearchDTO.getSearch()))
                                                            .or(song.four.contains(librarySearchDTO.getSearch()))
                                                            .or(song.five.contains(librarySearchDTO.getSearch()))
                                                            .or(song.six.contains(librarySearchDTO.getSearch()))
                                            )
                                            .or(
                                                    song.one.in(librarySearchDTO.getGenre())
                                                            .or(instIn(librarySearchDTO.getInst()))
                                                            .or(moodIn(librarySearchDTO.getMood()))
                                                            .or(playtimeIn(librarySearchDTO.getPlaytime()))
                                            )
                            );
                }
            }
        }
    }

    // 라이브러리 검색 옵션 모두 null 인지 체크
    private Boolean isNull(LibrarySearchDTO obj) {
        return (obj.getType().equals("모두") && obj.getSort().equals("업로드 날짜")
                && obj.getSearch().isEmpty() && obj.getGenre().isEmpty() && obj.getInst().isEmpty()
                && obj.getMood().isEmpty() && obj.getPlaytime().isEmpty() && obj.getTempo().isEmpty());
    }

    private BooleanExpression genreIn(List<String> genre) {
        return genre.isEmpty() ? null : song.one.in(genre);
    }

    private BooleanExpression instIn(List<String> inst) {
        return inst.isEmpty() ? null : song.four.in(inst);
    }

    // mood -> newsong::two, three, four
    // playtime -> newsong::playtime
    private BooleanExpression moodIn(List<String> mood) {
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
