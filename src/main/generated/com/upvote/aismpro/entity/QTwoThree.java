package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTwoThree is a Querydsl query type for TwoThree
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTwoThree extends EntityPathBase<TwoThree> {

    private static final long serialVersionUID = -907953506L;

    public static final QTwoThree twoThree = new QTwoThree("twoThree");

    public final StringPath three = createString("three");

    public final StringPath two = createString("two");

    public QTwoThree(String variable) {
        super(TwoThree.class, forVariable(variable));
    }

    public QTwoThree(Path<? extends TwoThree> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTwoThree(PathMetadata metadata) {
        super(TwoThree.class, metadata);
    }

}

