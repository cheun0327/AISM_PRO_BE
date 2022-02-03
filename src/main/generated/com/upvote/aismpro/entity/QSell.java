package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSell is a Querydsl query type for Sell
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSell extends EntityPathBase<Sell> {

    private static final long serialVersionUID = 1604312382L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSell sell = new QSell("sell");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSong song;

    public final QUser user;

    public QSell(String variable) {
        this(Sell.class, forVariable(variable), INITS);
    }

    public QSell(Path<? extends Sell> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSell(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSell(PathMetadata metadata, PathInits inits) {
        this(Sell.class, metadata, inits);
    }

    public QSell(Class<? extends Sell> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.song = inits.isInitialized("song") ? new QSong(forProperty("song"), inits.get("song")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

