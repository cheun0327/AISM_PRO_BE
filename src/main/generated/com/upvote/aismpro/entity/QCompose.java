package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompose is a Querydsl query type for Compose
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCompose extends EntityPathBase<Compose> {

    private static final long serialVersionUID = -1353610202L;

    public static final QCompose compose = new QCompose("compose");

    public final StringPath first_mood = createString("first_mood");

    public final StringPath genre = createString("genre");

    public final StringPath sample = createString("sample");

    public final StringPath second_mood = createString("second_mood");

    public QCompose(String variable) {
        super(Compose.class, forVariable(variable));
    }

    public QCompose(Path<? extends Compose> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompose(PathMetadata metadata) {
        super(Compose.class, metadata);
    }

}

