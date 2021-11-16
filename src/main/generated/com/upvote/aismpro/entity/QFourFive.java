package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QFourFive is a Querydsl query type for FourFive
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFourFive extends EntityPathBase<FourFive> {

    private static final long serialVersionUID = -2144097052L;

    public static final QFourFive fourFive = new QFourFive("fourFive");

    public final StringPath five = createString("five");

    public final StringPath four = createString("four");

    public QFourFive(String variable) {
        super(FourFive.class, forVariable(variable));
    }

    public QFourFive(Path<? extends FourFive> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFourFive(PathMetadata metadata) {
        super(FourFive.class, metadata);
    }

}

