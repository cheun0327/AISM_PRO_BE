package com.upvote.aismpro.customrepository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.dto.PlaylistDetailDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.entity.Playlist;
import com.upvote.aismpro.entity.QPlaylist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlaylistRepositoryImpl implements PlaylistRepositoryCustom{
    private final JPAQueryFactory query;
    private final QPlaylist playlist = QPlaylist.playlist;

    @Override
    public List<Playlist> findSimilarPlaylistQD(PlaylistDetailDTO playlistDetailDTO) {
        List<Playlist> pl = query.select(playlist)
                .from(playlist)
                .where(
                        playlist.one.in(playlistDetailDTO.getKeywords())
                                .or(playlist.two.in(playlistDetailDTO.getKeywords())
                                        .or(playlist.three.in(playlistDetailDTO.getKeywords())))
                )
                .fetch();

        if(pl.isEmpty()) return query.select(playlist).from(playlist).fetch();
        return pl;
    }

    @Override
    public List<Playlist> findNewSimilarPlaylistQD(SongDTO songDTO) {

        List<Playlist> pl = query.select(playlist)
                .from(playlist)
                .where(
                        playlist.one.in(songDTO.getTags())
                                .or(playlist.two.in(songDTO.getTags())
                                        .or(playlist.three.in(songDTO.getTags())))
                )
                .fetch();

        if(pl.isEmpty()) return query.select(playlist).from(playlist).fetch();

        return pl;
    }
}
