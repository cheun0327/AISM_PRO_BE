package com.upvote.aismpro.customrepository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.entity.QSong;
import com.upvote.aismpro.entity.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SongRepositoryQD implements SongRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private QSong song = QSong.song;

    public List<Song> findAllByIdListQD(List<String> songIdList){
        return queryFactory.select(song)
                .from(song)
                .where(songIdIn(songIdList))
                .fetch();
    }

    private BooleanExpression songIdIn(List<String> songIdList) {
        return songIdList.isEmpty() ? null : song.Id.in(songIdList);
    }

}
