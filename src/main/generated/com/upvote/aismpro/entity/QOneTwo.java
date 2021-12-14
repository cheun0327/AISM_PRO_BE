package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QOneTwo is a Querydsl query type for OneTwo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOneTwo extends EntityPathBase<OneTwo> {

    private static final long serialVersionUID = -255492878L;

    public static final QOneTwo oneTwo = new QOneTwo("oneTwo");

    public final NumberPath<Integer> indx = createNumber("indx", Integer.class);

    public final StringPath one = createString("one");

    public final StringPath two = createString("two");

    public QOneTwo(String variable) {
        super(OneTwo.class, forVariable(variable));
    }

    public QOneTwo(Path<? extends OneTwo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOneTwo(PathMetadata metadata) {
        super(OneTwo.class, metadata);
    }

}

