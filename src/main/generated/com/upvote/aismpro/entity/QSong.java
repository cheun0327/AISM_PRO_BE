package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSong is a Querydsl query type for Song
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSong extends EntityPathBase<Song> {

    private static final long serialVersionUID = 1604322049L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSong song = new QSong("song");

    public final StringPath createDate = createString("createDate");

    public final StringPath fileName = createString("fileName");

    public final StringPath firstMood = createString("firstMood");

    public final StringPath genre = createString("genre");

    public final StringPath length = createString("length");

    public final StringPath secondMood = createString("secondMood");

    public final StringPath songId = createString("songId");

    public final StringPath songName = createString("songName");

    public final StringPath thirdMood = createString("thirdMood");

    public final StringPath thumbnail = createString("thumbnail");

    public final StringPath type = createString("type");

    public final QUser user;

    public QSong(String variable) {
        this(Song.class, forVariable(variable), INITS);
    }

    public QSong(Path<? extends Song> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSong(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSong(PathMetadata metadata, PathInits inits) {
        this(Song.class, metadata, inits);
    }

    public QSong(Class<? extends Song> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

