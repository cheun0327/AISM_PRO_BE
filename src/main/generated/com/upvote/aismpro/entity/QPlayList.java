package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlayList is a Querydsl query type for PlayList
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPlayList extends EntityPathBase<PlayList> {

    private static final long serialVersionUID = -829346338L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlayList playList = new QPlayList("playList");

    public final StringPath firstMood = createString("firstMood");

    public final StringPath img = createString("img");

    public final StringPath name = createString("name");

    public final StringPath playlistId = createString("playlistId");

    public final StringPath secondMood = createString("secondMood");

    public final ListPath<Song, QSong> songs = this.<Song, QSong>createList("songs", Song.class, QSong.class, PathInits.DIRECT2);

    public final StringPath state = createString("state");

    public final StringPath thirdMood = createString("thirdMood");

    public final QUser user;

    public QPlayList(String variable) {
        this(PlayList.class, forVariable(variable), INITS);
    }

    public QPlayList(Path<? extends PlayList> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlayList(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlayList(PathMetadata metadata, PathInits inits) {
        this(PlayList.class, metadata, inits);
    }

    public QPlayList(Class<? extends PlayList> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

