package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QFX is a Querydsl query type for FX
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFX extends EntityPathBase<FX> {

    private static final long serialVersionUID = -1205033570L;

    public static final QFX fX = new QFX("fX");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath keyword = createString("keyword");

    public final StringPath realFileName = createString("realFileName");

    public final StringPath sampleFileName = createString("sampleFileName");

    public QFX(String variable) {
        super(FX.class, forVariable(variable));
    }

    public QFX(Path<? extends FX> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFX(PathMetadata metadata) {
        super(FX.class, metadata);
    }

}

