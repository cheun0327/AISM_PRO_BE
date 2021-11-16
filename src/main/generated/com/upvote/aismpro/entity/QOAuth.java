package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QOAuth is a Querydsl query type for OAuth
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOAuth extends EntityPathBase<OAuth> {

    private static final long serialVersionUID = -1810681269L;

    public static final QOAuth oAuth = new QOAuth("oAuth");

    public final StringPath email = createString("email");

    public final StringPath platform = createString("platform");

    public final StringPath userId = createString("userId");

    public QOAuth(String variable) {
        super(OAuth.class, forVariable(variable));
    }

    public QOAuth(Path<? extends OAuth> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOAuth(PathMetadata metadata) {
        super(OAuth.class, metadata);
    }

}

