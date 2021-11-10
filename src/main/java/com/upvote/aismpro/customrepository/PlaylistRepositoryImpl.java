package com.upvote.aismpro.customrepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.dto.MoodDTO;
import com.upvote.aismpro.entity.PlayList;
import com.upvote.aismpro.entity.QPlayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlaylistRepositoryImpl implements PlaylistRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final QPlayList playlist = QPlayList.playList;

    @Override
    public List<PlayList> findSimilarPlaylistQD(MoodDTO moodDTO) {
        return queryFactory.select(playlist)
                .from(playlist)
                .where(
                        playlist.playlistId.ne(moodDTO.getSongId())
                                .and(playlist.firstMood.eq(moodDTO.getFirstMood()))
                )
                .fetch();
    }
}
