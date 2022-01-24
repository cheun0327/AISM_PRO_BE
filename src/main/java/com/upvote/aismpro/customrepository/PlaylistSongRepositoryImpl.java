package com.upvote.aismpro.customrepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.entity.PlaylistSong;
import com.upvote.aismpro.entity.QPlaylistSong;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlaylistSongRepositoryImpl implements PlaylistSongRepositoryCustom {
    private final JPAQueryFactory query;
    private final QPlaylistSong playlistSong = QPlaylistSong.playlistSong;

    @Override
    public List<PlaylistSong> findPlaylistBySongIdQD(Long songId) {
        return query.selectFrom(playlistSong)
                .innerJoin(playlistSong.playlist).fetchJoin()
                .innerJoin(playlistSong.playlist.user).fetchJoin()
                .where(playlistSong.song.songId.eq(songId))
                .fetch();
    }

    @Override
    public List<PlaylistSong> findSavedSongListQD(Long playlistId, List<Long> songIdList) {
        return query.selectFrom(playlistSong)
                .innerJoin(playlistSong.song).fetchJoin()
                .innerJoin(playlistSong.song.user).fetchJoin()
                .where(
                        playlistSong.playlist.playlistId.eq(playlistId),
                        playlistSong.song.songId.in(songIdList)
                )
                .fetch();
    }
}
