package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseEventEntity is a Querydsl query type for BaseEventEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QBaseEventEntity extends EntityPathBase<BaseEventEntity> {

    private static final long serialVersionUID = 1376752160L;

    public static final QBaseEventEntity baseEventEntity = new QBaseEventEntity("baseEventEntity");

    public QBaseEventEntity(String variable) {
        super(BaseEventEntity.class, forVariable(variable));
    }

    public QBaseEventEntity(Path<? extends BaseEventEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseEventEntity(PathMetadata metadata) {
        super(BaseEventEntity.class, metadata);
    }

}

