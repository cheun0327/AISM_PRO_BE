package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QDummyKeywordPath is a Querydsl query type for DummyKeywordPath
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDummyKeywordPath extends EntityPathBase<DummyKeywordPath> {

    private static final long serialVersionUID = -1191440430L;

    public static final QDummyKeywordPath dummyKeywordPath = new QDummyKeywordPath("dummyKeywordPath");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath mood1 = createString("mood1");

    public final StringPath mood2 = createString("mood2");

    public final StringPath mood3 = createString("mood3");

    public QDummyKeywordPath(String variable) {
        super(DummyKeywordPath.class, forVariable(variable));
    }

    public QDummyKeywordPath(Path<? extends DummyKeywordPath> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDummyKeywordPath(PathMetadata metadata) {
        super(DummyKeywordPath.class, metadata);
    }

}

