package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QKeywordPath is a Querydsl query type for KeywordPath
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QKeywordPath extends EntityPathBase<KeywordPath> {

    private static final long serialVersionUID = 986352194L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QKeywordPath keywordPath = new QKeywordPath("keywordPath");

    public final StringPath category = createString("category");

    public final QFX fx;

    public final StringPath genre = createString("genre");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath keyword = createString("keyword");

    public final QSubCategory subCategory;

    public QKeywordPath(String variable) {
        this(KeywordPath.class, forVariable(variable), INITS);
    }

    public QKeywordPath(Path<? extends KeywordPath> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QKeywordPath(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QKeywordPath(PathMetadata metadata, PathInits inits) {
        this(KeywordPath.class, metadata, inits);
    }

    public QKeywordPath(Class<? extends KeywordPath> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fx = inits.isInitialized("fx") ? new QFX(forProperty("fx")) : null;
        this.subCategory = inits.isInitialized("subCategory") ? new QSubCategory(forProperty("subCategory")) : null;
    }

}

