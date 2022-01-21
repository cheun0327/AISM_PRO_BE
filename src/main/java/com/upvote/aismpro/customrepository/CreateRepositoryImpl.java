package com.upvote.aismpro.customrepository;


import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.dto.MyLibrarySearchDTO;
import com.upvote.aismpro.entity.Create;
import com.upvote.aismpro.entity.QCreate;
import com.upvote.aismpro.entity.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CreateRepositoryImpl implements CreateRepositoryCustom {
    private final JPAQueryFactory query;
    private final QCreate create = QCreate.create;

    @Override
    public List<Song> findMyLibraryCreateSearchQD(Long userId, MyLibrarySearchDTO myLibrarySearchDTO) {
        // TODO 정렬 : 디폴트로 날짜 정렬
        String search = myLibrarySearchDTO.getSearch();

        switch (myLibrarySearchDTO.getSort()) {
            case "업로드 날짜": {
                return searchOrderByDate(userId, myLibrarySearchDTO);
            }
            default: {
                return searchOrderByDate(userId, myLibrarySearchDTO);
            }
        }
    }

    // 유저가 작곡한 곡이 3개 이상인지 확인
    @Override
    public boolean isEnoughAddToPlaylistQD(Long userId) {
        long songCount = query.selectFrom(create)
                .where(create.user.userId.eq(userId))
                .fetchCount();

        return songCount >= 3;
    }

    @Override
    public List<Song> findSongListByUserIdLimit3QD(Long userId) {
        return query.selectFrom(create)
                .innerJoin(create.song).fetchJoin()
                .innerJoin(create.user).fetchJoin()
                .where(userId != null ? create.user.userId.eq(userId) : null)
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(3)
                .fetch()

                // create -> song
                .stream()
                .map(Create::getSong)
                .collect(Collectors.toList());
    }

    private List<Song> searchOrderByDate(Long userId, MyLibrarySearchDTO myLibrarySearchDTO) {
        String search = myLibrarySearchDTO.getSearch();

        List<Song> songs = query.select(create.song)
                .from(create)
                .where(
                        create.user.userId.eq(userId)
                                .and(
                                        create.song.songName.contains(search)
                                                .or(create.song.one.contains(search))
                                                .or(create.song.two.contains(search))
                                                .or(create.song.three.contains(search))
                                                .or(create.song.four.contains(search))
                                                .or(create.song.five.contains(search))
                                                .or(create.song.six.contains(search))
                                )
                )
                .orderBy(create.song.createDate.desc())
                .fetch();
        return songs;
    }

    private List<Song> searchRandom(Long userId, MyLibrarySearchDTO myLibrarySearchDTO) {
        String search = myLibrarySearchDTO.getSearch();

        List<Song> songs = query.select(create.song)
                .from(create)
                .where(
                        create.user.userId.eq(userId)
                                .and(
                                        create.song.songName.contains(search)
                                                .or(create.song.one.contains(search))
                                                .or(create.song.two.contains(search))
                                                .or(create.song.three.contains(search))
                                                .or(create.song.four.contains(search))
                                                .or(create.song.five.contains(search))
                                                .or(create.song.six.contains(search))
                                )
                )
                .fetch();
        return songs;
    }
}
