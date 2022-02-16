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
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.upvote.aismpro.entity.QKeywordPath.keywordPath;

@Repository
@RequiredArgsConstructor
public class SongRepositoryImpl implements SongRepositoryCustom {

    private final JPAQueryFactory query;
    private final QSong song = QSong.song;

    // --------------- 동적 쿼리 함수들 -----------------
    private BooleanExpression searchByKeyword(
            String searchKeyword, String type,
            List<String> genre, List<String> inst, List<String> mood
    ) {
        if (!StringUtils.hasText(searchKeyword)) {
            return null;
        }

        genre.add(searchKeyword);
        inst.add(searchKeyword);
        mood.add(searchKeyword);

        return song.songName.contains(searchKeyword)
                .or(song.user.nickname.contains(searchKeyword))
                .or(eqSongType(type))
                .or(inSongGenres(genre))
                .or(inSongInstruments(inst))
                .or(orSongMoodLike(mood));
    }

    private BooleanExpression eqSongType(String songType) {
        if (!StringUtils.hasText(songType)) {
            return null;
        }

        if (songType.equals("모두")) {
            return null;
        }

        return song.type.eq(songType);
    }

    private BooleanExpression inSongGenres(List<String> genres) {
        if (genres.isEmpty()) {
            return null;
        }

        return song.keywordPath.genre.in(genres);
    }

    private BooleanExpression inSongInstruments(List<String> instruments) {
        if (instruments.isEmpty()) {
            return null;
        }

        return song.keywordPath.keyword.in(instruments);
    }

    private BooleanExpression eqCategory(String category) {
        if (!StringUtils.hasText(category)) {
            return null;
        }

        return song.keywordPath.category.eq(category);
    }

    private BooleanExpression eqSubCategory(String subCategory) {
        if (!StringUtils.hasText(subCategory)) {
            return null;
        }

        return song.keywordPath.subCategory.keyword.eq(subCategory);
    }

    private BooleanExpression eqKeyword(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return null;
        }

        return song.keywordPath.keyword.eq(keyword);
    }

    private BooleanExpression eqFx(String fx) {
        if (!StringUtils.hasText(fx)) {
            return null;
        }

        return song.keywordPath.fx.keyword.eq(fx);
    }

    private BooleanExpression orSongMoodLike(List<String> mood) {
        if (mood.isEmpty()) {
            return null;
        }

        return addByRecursion(
                mood,
                0,
                song.keywordPath.category.in(mood)
                        .or(song.keywordPath.keyword.in(mood))
                        .or(keywordPath.subCategory.hashTag.contains("/" + mood.get(0) + "/"))
        );
    }

    private BooleanExpression addByRecursion(
            List<String> moodList,
            int index,
            BooleanExpression like
    ) {
        if (index == moodList.size() - 1) {
            return like;
        }

        return addByRecursion(
                moodList,
                index + 1,
                like.or(keywordPath.subCategory.hashTag.contains("/" + moodList.get(index + 1) + "/"))
        );
    }
    // -----------------------------------------------

    // 비슷한 곡 리스트 반환
    @Override
    public List<Song> findSimilarSongQD(
            Long songId,
            String genre,
            String category,
            String subCategory,
            String keyword,
            String fx
    ) {
        return query
                .selectFrom(song)
                .innerJoin(song.keywordPath, keywordPath).fetchJoin()
                .innerJoin(keywordPath.subCategory).fetchJoin()
                .innerJoin(keywordPath.fx).fetchJoin()
                .where(
                        song.songId.ne(songId),
                        keywordPath.genre.eq(genre)
                                .or(eqCategory(category))
                                .or(eqSubCategory(subCategory))
                                .or(eqKeyword(keyword))
                                .or(eqFx(fx))
                )
                .limit(6)
                .fetch();
    }

    @Override
    public List<Song> findSimilarSongByTagsQD(SongTagDTO songTagDTO) {
        String genre = songTagDTO.getOne();
        String category = songTagDTO.getTwo();
        String subCategory = songTagDTO.getThree();
        String keyword = songTagDTO.getFour();
        String fx = songTagDTO.getFive();

        List<Song> songs = query
                .selectFrom(song)
                .innerJoin(song.keywordPath, keywordPath).fetchJoin()
                .innerJoin(keywordPath.subCategory).fetchJoin()
                .innerJoin(keywordPath.fx).fetchJoin()
                .where(
                        keywordPath.genre.eq(genre)
                                .and(eqCategory(category))
                                .or(eqSubCategory(subCategory))
                                .or(eqKeyword(keyword))
                                .or(eqFx(fx))
                )
                .fetch();

        if (songs.isEmpty()) {
            return query
                    .selectFrom(song)
                    .fetch();
        }

        return songs;
    }

    // 라이브러리 검색 결과 반환
    @Override
    public List<Song> findSongBySearchParamQD(
            String searchKeyword, String type, String sort,
            List<String> genre, List<String> inst, List<String> mood
    ) {
        return query
                .selectFrom(song)
                .innerJoin(song.user).fetchJoin()
                .innerJoin(song.keywordPath, keywordPath).fetchJoin()
                .innerJoin(keywordPath.subCategory).fetchJoin()
                .innerJoin(keywordPath.fx).fetchJoin()

                .where(
                        song.deletedDate.isNull(),
                        eqSongType(type),
                        inSongGenres(genre),
                        inSongInstruments(inst),
                        orSongMoodLike(mood),
                        searchByKeyword(searchKeyword, type, genre, inst, mood)
                )

                .orderBy(sort.equals("업로드 날짜") ? song.songId.desc() : null)

                .limit(6)
                .fetch();
    }

    // 라이브러리 검색 이후 전체보기 페이지
    // TODO Paging 처리 적용하기 -> 무한 스크롤 개발 시점에
    @Override
    public List<Song> findLibraryTotalSongSearchQD(
            Pageable pageable,
            LibrarySearchDTO librarySearchDTO
    ) {
        return query
                .selectFrom(song)
                .innerJoin(song.user).fetchJoin()
                .innerJoin(song.keywordPath, keywordPath).fetchJoin()
                .innerJoin(keywordPath.subCategory).fetchJoin()
                .innerJoin(keywordPath.fx).fetchJoin()

                .where(
                        song.deletedDate.isNull(),
                        eqSongType(librarySearchDTO.getType()),
                        inSongGenres(librarySearchDTO.getGenre()),
                        inSongInstruments(librarySearchDTO.getInst()),
                        orSongMoodLike(librarySearchDTO.getMood()),
                        searchByKeyword(
                                librarySearchDTO.getSearch(),
                                librarySearchDTO.getType(),
                                librarySearchDTO.getGenre(),
                                librarySearchDTO.getInst(),
                                librarySearchDTO.getMood()
                        )
                )

                .orderBy(librarySearchDTO.getSort().equals("업로드 날짜") ? song.songId.desc() : null)

                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<User> findLibraryTotalArtistSearchQD(Pageable pageable, String search) {

        if (!search.equals("")) {
            System.out.println(pageable);
            QueryResults<Song> results = query
                    .selectDistinct(song)
                    .from(song)
                    .innerJoin(song.user).fetchJoin()

                    .where(song.user.nickname.contains(search))

                    .offset(pageable.getOffset())
                    .limit(15)

                    .fetchResults();

            return results.getResults().stream()
                    .map(Song::getUser)
                    .collect(Collectors.toList());
        } else {
            System.out.println("결과 없음");
            QueryResults<Song> results = query
                    .selectDistinct(song)
                    .from(song)
                    .innerJoin(song.user)

                    .offset(pageable.getOffset())
                    .limit(15)

                    .fetchResults();

            return results.getResults().stream()
                    .map(Song::getUser)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Song> findAllByIdFetchUserQD(List<Long> songIdList) {
        return query.selectFrom(song)
                .innerJoin(song.user).fetchJoin()
                .where(
                        song.songId.in(songIdList),
                        song.deletedDate.isNull()
                )
                .fetch();
    }

    @Override
    public List<Song> findAllByUserIdFetchUserQD(Long userId) {
        return query
                .selectFrom(song)
                .innerJoin(song.user).fetchJoin()
                .innerJoin(song.keywordPath, keywordPath).fetchJoin()
                .innerJoin(keywordPath.subCategory).fetchJoin()
                .innerJoin(keywordPath.fx).fetchJoin()
                .where(
                        song.user.userId.eq(userId),
                        song.deletedDate.isNull()
                )
                .orderBy(song.songId.desc())
                .fetch();
    }

    @Override
    public List<Song> searchSongListQD(Long userId, String searchStr) {
        return query
                .selectFrom(song)
                .innerJoin(song.user).fetchJoin()
                .innerJoin(song.keywordPath, keywordPath).fetchJoin()
                .innerJoin(keywordPath.subCategory).fetchJoin()
                .innerJoin(keywordPath.fx).fetchJoin()
                .where(
                        song.user.userId.eq(userId),
                        song.deletedDate.isNull(),
                        song.songName.contains(searchStr)
                                .or(keywordPath.genre.contains(searchStr))
                                .or(keywordPath.category.contains(searchStr))
                                .or(keywordPath.subCategory.keyword.contains(searchStr))
                                .or(keywordPath.keyword.contains(searchStr))
                                .or(keywordPath.fx.keyword.contains(searchStr))
                )
                .orderBy(song.songId.desc())
                .fetch();
    }

    // 유저가 작곡한 곡이 3개 이상인지 확인
    @Override
    public boolean isEnoughAddToPlaylistQD(Long userId) {
        long songCount = query
                .selectFrom(song)
                .where(
                        song.user.userId.eq(userId),
                        song.deletedDate.isNull()
                )
                .fetchCount();

        return songCount >= 3;
    }

    @Override
    public List<Song> findSongListByUserIdLimit3QD(Long userId) {
        return query
                .selectFrom(song)
                .innerJoin(song.user).fetchJoin()
                .where(
                        song.deletedDate.isNull(),
                        userId != null ? song.user.userId.eq(userId) : null
                )
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(3)
                .fetch();
    }

    @Override
    public Song findByIdFetchKeywordPathQD(Long songId) {
        return query
                .selectFrom(song)
                .innerJoin(song.keywordPath, keywordPath).fetchJoin()
                .innerJoin(keywordPath.subCategory).fetchJoin()
                .innerJoin(keywordPath.fx).fetchJoin()
                .where(song.songId.eq(songId))
                .fetchOne();
    }

    // 라이브러리 검색 결과 업로드 날짜로 정렬 반환
    private Page<Song> searchOrderByDate(LibrarySearchDTO librarySearchDTO) {
        QueryResults<Song> results = query
                .selectFrom(song)
                .innerJoin(song.user).fetchJoin()
                .innerJoin(song.keywordPath, keywordPath).fetchJoin()
                .innerJoin(keywordPath.subCategory).fetchJoin()
                .innerJoin(keywordPath.fx).fetchJoin()

                .where(
                        searchWhere(librarySearchDTO),
                        song.deletedDate.isNull()
                )
                .orderBy(song.songId.desc())
                .offset(0)
                .limit(6)
                .fetchResults();

        return new PageImpl<>(results.getResults());
    }

    // 라이브러리 검색 결과 그냥 반환
    private Page<Song> search(LibrarySearchDTO librarySearchDTO) {
        QueryResults<Song> results = query
                .selectFrom(song)
                .innerJoin(song.user).fetchJoin()
                .innerJoin(song.keywordPath, keywordPath).fetchJoin()
                .innerJoin(keywordPath.subCategory).fetchJoin()
                .innerJoin(keywordPath.fx).fetchJoin()

                .where(
                        searchWhere(librarySearchDTO),
                        song.deletedDate.isNull()
                )
                .offset(0)
                .limit(6)

                .fetchResults();

        return new PageImpl<>(results.getResults());
    }

    //// ㄹㅏ이브러리 검색 전체보기
    private Page<Song> pagingSearchOrderByDate(Pageable pageable, LibrarySearchDTO librarySearchDTO) {
        QueryResults<Song> results = query
                .selectFrom(song)
                .innerJoin(song.user).fetchJoin()
                .innerJoin(song.keywordPath, keywordPath).fetchJoin()
                .innerJoin(keywordPath.subCategory).fetchJoin()
                .innerJoin(keywordPath.fx).fetchJoin()

                .where(
                        song.deletedDate.isNull(),
                        searchWhere(librarySearchDTO)
                )

                .orderBy(song.songId.desc())

                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())

                .fetchResults();

        return new PageImpl<>(results.getResults());
    }

    private Page<Song> pagingSearch(Pageable pageable, LibrarySearchDTO librarySearchDTO) {
        QueryResults<Song> results = query
                .selectFrom(song)
                .innerJoin(song.user).fetchJoin()
                .innerJoin(song.keywordPath, keywordPath).fetchJoin()
                .innerJoin(keywordPath.subCategory).fetchJoin()
                .innerJoin(keywordPath.fx).fetchJoin()

                .where(
                        song.deletedDate.isNull(),
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
            List<User> results = query
                    .selectDistinct(song)
                    .from(song)
                    .innerJoin(song.user)
                    .fetch()

                    .stream()
                    .map(Song::getUser)
                    .collect(Collectors.toList());

            Collections.shuffle(results);

            return Lists.newArrayList(results.subList(0, 9));
        } else {
            QueryResults<Song> results = query
                    .selectDistinct(song)
                    .from(song)
                    .innerJoin(song.user)

                    .where(song.user.nickname.contains(search))

                    .offset(0)
                    .limit(10)

                    .fetchResults();

            return results.getResults().stream()
                    .map(Song::getUser)
                    .collect(Collectors.toList());
        }
    }

    // 라이브러리 검색 공통 where문
    private BooleanExpression searchWhere(LibrarySearchDTO librarySearchDTO) {
        if (librarySearchDTO.getSearch().equals("") || librarySearchDTO.getSearch() == null) {
            System.out.println("키워드 없음");
            switch (librarySearchDTO.getType()) {
                case "모두": {
                    return song.keywordPath.genre.in(librarySearchDTO.getGenre())
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
                                    song.keywordPath.genre.contains(librarySearchDTO.getSearch())
                                            .or(song.keywordPath.category.contains(librarySearchDTO.getSearch()))
                                            .or(song.keywordPath.subCategory.hashTag.contains("/" + librarySearchDTO.getSearch() + "/"))
                                            .or(song.keywordPath.keyword.contains(librarySearchDTO.getSearch()))
                                            .or(song.keywordPath.fx.keyword.contains(librarySearchDTO.getSearch()))
                            )
                            .or(
                                    song.keywordPath.genre.in(librarySearchDTO.getGenre())
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
                                                    song.keywordPath.genre.contains(librarySearchDTO.getSearch())
                                                            .or(song.keywordPath.category.contains(librarySearchDTO.getSearch()))
                                                            .or(song.keywordPath.subCategory.hashTag.contains("/" + librarySearchDTO.getSearch() + "/"))
                                                            .or(song.keywordPath.keyword.contains(librarySearchDTO.getSearch()))
                                                            .or(song.keywordPath.fx.keyword.contains(librarySearchDTO.getSearch()))
                                            )
                                            .or(
                                                    song.keywordPath.genre.in(librarySearchDTO.getGenre())
                                                            .or(instIn(librarySearchDTO.getInst()))
                                                            .or(moodIn(librarySearchDTO.getMood()))
                                                            .or(playtimeIn(librarySearchDTO.getPlaytime()))
                                            )
                            );
                }
            }
        }
    }

    private BooleanExpression genreIn(List<String> genre) {
        return genre.isEmpty() ? null : song.keywordPath.genre.in(genre);
    }

    private BooleanExpression instIn(List<String> inst) {
        return inst.isEmpty() ? null : song.keywordPath.keyword.in(inst);
    }

    // mood -> newsong::two, three, four
    // playtime -> newsong::playtime
    private BooleanExpression moodIn(List<String> mood) {
        return mood.isEmpty() ? null : song.keywordPath.category.in(mood)
                .or(song.keywordPath.subCategory.hashTag.in(mood.stream().map(str -> "/" + str + "/").collect(Collectors.toList())))
                .or(song.keywordPath.keyword.in(mood));
    }

    private BooleanExpression playtimeIn(List<String> playtime) {
        return playtime.isEmpty() ? null : song.playtime.in(playtime);
    }
}
