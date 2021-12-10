package com.upvote.aismpro.customrepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.dto.MyLibrarySearchDTO;
import com.upvote.aismpro.entity.QSell;
import com.upvote.aismpro.entity.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SellRepositoryImpl implements  SellRepositoryCustom{
    private final JPAQueryFactory query;
    private final QSell sell = QSell.sell;

    @Override
    public List<Song> findMyLibrarySellSearchQD(Long userId, MyLibrarySearchDTO myLibrarySearchDTO) {
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

        List<Song> songs = query.select(sell.song)
                .from(sell)
                .where(
                        sell.user.userId.eq(userId)
                                        .and(
                                                sell.song.songName.contains(search)
                                                        .or(sell.song.one.contains(search))
                                                        .or(sell.song.two.contains(search))
                                                        .or(sell.song.three.contains(search))
                                                        .or(sell.song.four.contains(search))
                                                        .or(sell.song.five.contains(search))
                                                        .or(sell.song.six.contains(search))
                                        )
                )
                .orderBy(sell.song.createDate.desc())
                .fetch();
        return songs;
    }

    private List<Song> searchRandom(Long userId, MyLibrarySearchDTO myLibrarySearchDTO) {
        String search = myLibrarySearchDTO.getSearch();

        List<Song> songs = query.select(sell.song)
                .from(sell)
                .where(
                        sell.user.userId.eq(userId)
                                .and(
                                        sell.song.songName.contains(search)
                                                .or(sell.song.one.contains(search))
                                                .or(sell.song.two.contains(search))
                                                .or(sell.song.three.contains(search))
                                                .or(sell.song.four.contains(search))
                                                .or(sell.song.five.contains(search))
                                                .or(sell.song.six.contains(search))
                                )
                )
                .fetch();
        return songs;
    }
}
