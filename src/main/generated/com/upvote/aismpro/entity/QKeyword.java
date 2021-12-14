package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QKeyword is a Querydsl query type for Keyword
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QKeyword extends EntityPathBase<Keyword> {

    private static final long serialVersionUID = 1176451197L;

    public static final QKeyword keyword = new QKeyword("keyword");

    public final StringPath five = createString("five");

    public final StringPath four = createString("four");

    public final StringPath one = createString("one");

    public final StringPath six = createString("six");

    public final StringPath three = createString("three");

    public final StringPath two = createString("two");

    public QKeyword(String variable) {
        super(Keyword.class, forVariable(variable));
    }

    public QKeyword(Path<? extends Keyword> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKeyword(PathMetadata metadata) {
        super(Keyword.class, metadata);
    }

}

