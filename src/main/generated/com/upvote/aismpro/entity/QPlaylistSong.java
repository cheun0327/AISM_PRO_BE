package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPlaylistSong is a Querydsl query type for PlaylistSong
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPlaylistSong extends EntityPathBase<PlaylistSong> {

    private static final long serialVersionUID = -1598549005L;

    public static final QPlaylistSong playlistSong = new QPlaylistSong("playlistSong");

    public final NumberPath<Long> playlistId = createNumber("playlistId", Long.class);

    public final NumberPath<Long> songId = createNumber("songId", Long.class);

    public QPlaylistSong(String variable) {
        super(PlaylistSong.class, forVariable(variable));
    }

    public QPlaylistSong(Path<? extends PlaylistSong> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlaylistSong(PathMetadata metadata) {
        super(PlaylistSong.class, metadata);
    }

}

