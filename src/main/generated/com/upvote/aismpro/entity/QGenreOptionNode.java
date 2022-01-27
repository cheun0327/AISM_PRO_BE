package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGenreOptionNode is a Querydsl query type for GenreOptionNode
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGenreOptionNode extends EntityPathBase<GenreOptionNode> {

    private static final long serialVersionUID = -1952854898L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGenreOptionNode genreOptionNode = new QGenreOptionNode("genreOptionNode");

    public final NumberPath<Integer> depth = createNumber("depth", Integer.class);

    public final QGenre genre;

    public final QGenreOption genreOption;

    public final NumberPath<Long> nodeId = createNumber("nodeId", Long.class);

    public final QGenreOptionNode parentNode;

    public final StringPath path = createString("path");

    public QGenreOptionNode(String variable) {
        this(GenreOptionNode.class, forVariable(variable), INITS);
    }

    public QGenreOptionNode(Path<? extends GenreOptionNode> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGenreOptionNode(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGenreOptionNode(PathMetadata metadata, PathInits inits) {
        this(GenreOptionNode.class, metadata, inits);
    }

    public QGenreOptionNode(Class<? extends GenreOptionNode> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.genre = inits.isInitialized("genre") ? new QGenre(forProperty("genre")) : null;
        this.genreOption = inits.isInitialized("genreOption") ? new QGenreOption(forProperty("genreOption")) : null;
        this.parentNode = inits.isInitialized("parentNode") ? new QGenreOptionNode(forProperty("parentNode"), inits.get("parentNode")) : null;
    }

}

