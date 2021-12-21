package com.upvote.aismpro.customrepository;


import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.entity.Playlist;
import com.upvote.aismpro.entity.QPlaylist;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
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

        // TODO 개수 제한
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

        // TODO 개수 제한
        if(pl.isEmpty()) return query.select(playlist).from(playlist).fetch();

        return pl;
    }

    @Override
    public List<Playlist> findNewSimilarPlaylistByTagsQD(SongTagDTO songTagDTO) {
        // TODO song category 개수 변동
        List<String> tags = new ArrayList<>(Arrays.asList(songTagDTO.getOne(), songTagDTO.getTwo(),
                songTagDTO.getThree(), songTagDTO.getFour(), songTagDTO.getFive(), songTagDTO.getSix()));

        List<Playlist> pl = query.select(playlist)
                .from(playlist)
                .where(
                        playlist.one.in(tags)
                                .or(playlist.two.in(tags))
                                .or(playlist.three.in(tags))
                )
                .fetch();
        // TODO 개수 제한
        if(pl.isEmpty()) return query.select(playlist).from(playlist).fetch();

        return pl;
    }

    @Override
    public List<Playlist> findMyLibraryPlaylistSearchQD(Long userId, MyLibrarySearchDTO myLibrarySearchDTO) {
        List<Playlist> pl = query.select(playlist)
                .from(playlist)
                .where(
                        playlist.user.userId.eq(userId)
                                        .and(
                                                playlist.name.contains(myLibrarySearchDTO.getSearch())
                                                        .or(playlist.one.contains(myLibrarySearchDTO.getSearch()))
                                                        .or(playlist.two.contains(myLibrarySearchDTO.getSearch()))
                                                        .or(playlist.three.contains(myLibrarySearchDTO.getSearch()))
                                        )
                )
                .fetch();
        return pl;
    }

    // Library 플리이리스트 검색 결과
    @Override
    public Page<Playlist> findLibraryTotalPlaylistSearchQD(LibrarySearchDTO librarySearchDTO) {

        QueryResults<Playlist> result = query.select(playlist)
                .from(playlist)
                .where(
                        playlist.name.contains(librarySearchDTO.getSearch())
                                .or(playlist.user.nickname.contains(librarySearchDTO.getSearch()))
                                .or(playlist.one.contains(librarySearchDTO.getSearch()))
                                .or(playlist.two.contains(librarySearchDTO.getSearch()))
                                .or(playlist.three.contains(librarySearchDTO.getSearch()))
                )
                .offset(0)
                .limit(15)
                .orderBy(playlist.createDate.desc())
                .fetchResults();

        return new PageImpl<>(result.getResults());
    }
}
