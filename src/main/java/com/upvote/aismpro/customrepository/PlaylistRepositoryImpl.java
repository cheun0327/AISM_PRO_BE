package com.upvote.aismpro.customrepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.dto.MoodDTO;
import com.upvote.aismpro.dto.NewPlaylistDTO;
import com.upvote.aismpro.dto.NewSongDTO;
import com.upvote.aismpro.dto.PlaylistDetailDTO;
import com.upvote.aismpro.entity.NewSong;
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

    @Override
    public List<PlayList> findSimilarPlaylistPlaylistQD(PlaylistDetailDTO playlistDetailDTO) {
        List<PlayList> pl = queryFactory.select(playlist)
                .from(playlist)
                .where(
                        playlist.firstMood.in(playlistDetailDTO.getKeywords())
                                .or(playlist.secondMood.in(playlistDetailDTO.getKeywords())
                                        .or(playlist.secondMood.in(playlistDetailDTO.getKeywords())))
                )
                .fetch();

        if(pl.isEmpty()) return queryFactory.select(playlist).from(playlist).fetch();

        return pl;
    }

    @Override
    public List<PlayList> findNewSimilarPlaylistQD(NewSongDTO songDTO) {

        List<PlayList> pl = queryFactory.select(playlist)
                .from(playlist)
                .where(
                        playlist.firstMood.in(songDTO.getTag())
                                .or(playlist.secondMood.in(songDTO.getTag())
                                        .or(playlist.secondMood.in(songDTO.getTag())))
                )
                .fetch();

        if(pl.isEmpty()) return queryFactory.select(playlist).from(playlist).fetch();

        return pl;
    }

}
