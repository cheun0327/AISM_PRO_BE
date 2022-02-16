package com.upvote.aismpro.customrepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.dto.MyLibrarySearchDTO;
import com.upvote.aismpro.entity.QBuy;
import com.upvote.aismpro.entity.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BuyRepositoryImpl implements BuyRepositoryCustom{
    private final JPAQueryFactory query;
    private final QBuy buy = QBuy.buy;

    // MyLibraru 생성 음원 검색 결과
    @Override
    public List<Song> findMyLibraryBuySearchQD(Long userId, MyLibrarySearchDTO myLibrarySearchDTO) {
        // TODO 정렬 : 디폴트로 날짜 정렬
//        String search = myLibrarySearchDTO.getSearch();
//
//        switch (myLibrarySearchDTO.getSort()) {
//            case "업로드 날짜": {
//                return searchOrderByDate(userId, myLibrarySearchDTO);
//            }
//            default: {
//                return searchOrderByDate(userId, myLibrarySearchDTO);
//            }
//        }
        return null;
    }

    private List<Song> searchOrderByDate(Long userId, MyLibrarySearchDTO myLibrarySearchDTO) {
//        String search = myLibrarySearchDTO.getSearch();
//
//        List<Song> songs = query.select(buy.song)
//                .from(buy)
//                .where(
//                        buy.user.userId.eq(userId)
//                                        .and(
//                                                buy.song.songName.contains(search)
//                                                        .or(buy.song.one.contains(search))
//                                                        .or(buy.song.two.contains(search))
//                                                        .or(buy.song.three.contains(search))
//                                                        .or(buy.song.four.contains(search))
//                                                        .or(buy.song.five.contains(search))
//                                                        .or(buy.song.six.contains(search))
//                                        )
//                )
//                .orderBy(buy.song.createDate.desc())
//                .fetch();
//        return songs;
        return null;
    }

    private List<Song> searchRandom(Long userId, MyLibrarySearchDTO myLibrarySearchDTO) {
//        String search = myLibrarySearchDTO.getSearch();
//
//        List<Song> songs = query.select(buy.song)
//                .from(buy)
//                .where(
//                        buy.user.userId.eq(userId)
//                                .and(
//                                        buy.song.songName.contains(search)
//                                                .or(buy.song.one.contains(search))
//                                                .or(buy.song.two.contains(search))
//                                                .or(buy.song.three.contains(search))
//                                                .or(buy.song.four.contains(search))
//                                                .or(buy.song.five.contains(search))
//                                                .or(buy.song.six.contains(search))
//                                )
//                )
//                .fetch();
//        return songs;
        return null;
    }
}
