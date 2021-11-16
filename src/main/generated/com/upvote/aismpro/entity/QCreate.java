package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCreate is a Querydsl query type for Create
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCreate extends EntityPathBase<Create> {

    private static final long serialVersionUID = -595336216L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCreate create = new QCreate("create");

    public final StringPath id = createString("id");

    public final QSong song;

    public final QUser user;

    public QCreate(String variable) {
        this(Create.class, forVariable(variable), INITS);
    }

    public QCreate(Path<? extends Create> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCreate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCreate(PathMetadata metadata, PathInits inits) {
        this(Create.class, metadata, inits);
    }

    public QCreate(Class<? extends Create> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.song = inits.isInitialized("song") ? new QSong(forProperty("song"), inits.get("song")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

