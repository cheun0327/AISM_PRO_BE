package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNewSong is a Querydsl query type for NewSong
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNewSong extends EntityPathBase<NewSong> {

    private static final long serialVersionUID = -458924695L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNewSong newSong = new QNewSong("newSong");

    public final DateTimePath<java.util.Date> createDate = createDateTime("createDate", java.util.Date.class);

    public final StringPath fileName = createString("fileName");

    public final StringPath five = createString("five");

    public final StringPath four = createString("four");

    public final StringPath one = createString("one");

    public final StringPath playtime = createString("playtime");

    public final StringPath songId = createString("songId");

    public final StringPath songName = createString("songName");

    public final StringPath three = createString("three");

    public final StringPath thumbnail = createString("thumbnail");

    public final StringPath two = createString("two");

    public final StringPath type = createString("type");

    public final QUser user;

    public QNewSong(String variable) {
        this(NewSong.class, forVariable(variable), INITS);
    }

    public QNewSong(Path<? extends NewSong> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNewSong(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNewSong(PathMetadata metadata, PathInits inits) {
        this(NewSong.class, metadata, inits);
    }

    public QNewSong(Class<? extends NewSong> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

