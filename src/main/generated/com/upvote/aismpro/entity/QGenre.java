package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QGenre is a Querydsl query type for Genre
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGenre extends EntityPathBase<Genre> {

    private static final long serialVersionUID = -1817003753L;

    public static final QGenre genre = new QGenre("genre");

    public final NumberPath<Long> genreId = createNumber("genreId", Long.class);

    public final StringPath genreName = createString("genreName");

    public final NumberPath<Integer> genreOrder = createNumber("genreOrder", Integer.class);

    public QGenre(String variable) {
        super(Genre.class, forVariable(variable));
    }

    public QGenre(Path<? extends Genre> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGenre(PathMetadata metadata) {
        super(Genre.class, metadata);
    }

}

