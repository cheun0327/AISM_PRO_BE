package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGenreOptionOrder is a Querydsl query type for GenreOptionOrder
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGenreOptionOrder extends EntityPathBase<GenreOptionOrder> {

    private static final long serialVersionUID = -407946686L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGenreOptionOrder genreOptionOrder = new QGenreOptionOrder("genreOptionOrder");

    public final QGenre genre;

    public final QGenreOption genreOption;

    public final NumberPath<Integer> order = createNumber("order", Integer.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public QGenreOptionOrder(String variable) {
        this(GenreOptionOrder.class, forVariable(variable), INITS);
    }

    public QGenreOptionOrder(Path<? extends GenreOptionOrder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGenreOptionOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGenreOptionOrder(PathMetadata metadata, PathInits inits) {
        this(GenreOptionOrder.class, metadata, inits);
    }

    public QGenreOptionOrder(Class<? extends GenreOptionOrder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.genre = inits.isInitialized("genre") ? new QGenre(forProperty("genre")) : null;
        this.genreOption = inits.isInitialized("genreOption") ? new QGenreOption(forProperty("genreOption")) : null;
    }

}

