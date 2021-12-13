package com.upvote.aismpro.customrepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.dto.MyLibrarySearchDTO;
import com.upvote.aismpro.entity.QLike;
import com.upvote.aismpro.entity.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepositoryCustom{
    private final JPAQueryFactory query;
    private final QLike like = QLike.like;

    @Override
    public List<Song> findMyLibraryLikeSearchQD(Long userId, MyLibrarySearchDTO myLibrarySearchDTO) {
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

    private List<Song> searchOrderByDate(Long userId, MyLibrarySearchDTO myLibrarySearchDTO) {
        String search = myLibrarySearchDTO.getSearch();

        List<Song> songs = query.select(like.song)
                .from(like)
                .where(
                        like.user.userId.eq(userId)
                                        .and(
                                                like.song.songName.contains(search)
                                                        .or(like.song.one.contains(search))
                                                        .or(like.song.two.contains(search))
                                                        .or(like.song.three.contains(search))
                                                        .or(like.song.four.contains(search))
                                                        .or(like.song.five.contains(search))
                                                        .or(like.song.six.contains(search))
                                        )
                )
                .orderBy(like.song.createDate.desc())
                .fetch();
        return songs;
    }

    private List<Song> searchRandom(Long userId, MyLibrarySearchDTO myLibrarySearchDTO) {
        String search = myLibrarySearchDTO.getSearch();

        List<Song> songs = query.select(like.song)
                .from(like)
                .where(
                        like.user.userId.eq(userId)
                                .and(
                                        like.song.songName.contains(search)
                                                .or(like.song.one.contains(search))
                                                .or(like.song.two.contains(search))
                                                .or(like.song.three.contains(search))
                                                .or(like.song.four.contains(search))
                                                .or(like.song.five.contains(search))
                                                .or(like.song.six.contains(search))
                                )
                )
                .fetch();
        return songs;
    }
}
