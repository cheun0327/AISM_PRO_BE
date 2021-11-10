package com.upvote.aismpro.customrepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.entity.PlayList;
import com.upvote.aismpro.entity.PlayListSong;
import com.upvote.aismpro.entity.QPlayListSong;

import java.util.List;

public class PlaylistSongRepositoryImpl implements PlaylistSongRepositoryCustom {
    private JPAQueryFactory query;
    private final QPlayListSong playListSong = QPlayListSong.playListSong;

    @Override
    public List<PlayListSong> findSavedPlaylistBySongId(String songId) {
        return query.select(playListSong)
                .from(playListSong)
                .where(
                    playListSong.songId.eq(songId)
                )
                .fetch();
    }
}
