package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaylistSong is a Querydsl query type for PlaylistSong
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPlaylistSong extends EntityPathBase<PlaylistSong> {

    private static final long serialVersionUID = -1598549005L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaylistSong playlistSong = new QPlaylistSong("playlistSong");

    public final QPlaylist playlist;

    public final QSong song;

    public QPlaylistSong(String variable) {
        this(PlaylistSong.class, forVariable(variable), INITS);
    }

    public QPlaylistSong(Path<? extends PlaylistSong> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaylistSong(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaylistSong(PathMetadata metadata, PathInits inits) {
        this(PlaylistSong.class, metadata, inits);
    }

    public QPlaylistSong(Class<? extends PlaylistSong> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.playlist = inits.isInitialized("playlist") ? new QPlaylist(forProperty("playlist"), inits.get("playlist")) : null;
        this.song = inits.isInitialized("song") ? new QSong(forProperty("song"), inits.get("song")) : null;
    }

}

