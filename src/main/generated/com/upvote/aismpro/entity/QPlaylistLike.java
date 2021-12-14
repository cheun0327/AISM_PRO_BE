package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaylistLike is a Querydsl query type for PlaylistLike
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPlaylistLike extends EntityPathBase<PlaylistLike> {

    private static final long serialVersionUID = -1598763403L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaylistLike playlistLike = new QPlaylistLike("playlistLike");

    public final NumberPath<Long> Id = createNumber("Id", Long.class);

    public final QPlaylist playlist;

    public final QUser user;

    public QPlaylistLike(String variable) {
        this(PlaylistLike.class, forVariable(variable), INITS);
    }

    public QPlaylistLike(Path<? extends PlaylistLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaylistLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaylistLike(PathMetadata metadata, PathInits inits) {
        this(PlaylistLike.class, metadata, inits);
    }

    public QPlaylistLike(Class<? extends PlaylistLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.playlist = inits.isInitialized("playlist") ? new QPlaylist(forProperty("playlist"), inits.get("playlist")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

