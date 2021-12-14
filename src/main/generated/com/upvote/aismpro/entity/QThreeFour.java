package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QThreeFour is a Querydsl query type for ThreeFour
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QThreeFour extends EntityPathBase<ThreeFour> {

    private static final long serialVersionUID = 396029304L;

    public static final QThreeFour threeFour = new QThreeFour("threeFour");

    public final StringPath four = createString("four");

    public final StringPath three = createString("three");

    public QThreeFour(String variable) {
        super(ThreeFour.class, forVariable(variable));
    }

    public QThreeFour(Path<? extends ThreeFour> path) {
        super(path.getType(), path.getMetadata());
    }

    public QThreeFour(PathMetadata metadata) {
        super(ThreeFour.class, metadata);
    }

}

