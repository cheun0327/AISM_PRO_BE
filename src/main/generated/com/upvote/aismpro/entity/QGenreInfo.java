package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QGenreInfo is a Querydsl query type for GenreInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGenreInfo extends EntityPathBase<GenreInfo> {

    private static final long serialVersionUID = -1693110683L;

    public static final QGenreInfo genreInfo = new QGenreInfo("genreInfo");

    public final StringPath five = createString("five");

    public final StringPath four = createString("four");

    public final StringPath genre = createString("genre");

    public final NumberPath<Integer> idx = createNumber("idx", Integer.class);

    public final StringPath one = createString("one");

    public final StringPath six = createString("six");

    public final StringPath three = createString("three");

    public final StringPath two = createString("two");

    public QGenreInfo(String variable) {
        super(GenreInfo.class, forVariable(variable));
    }

    public QGenreInfo(Path<? extends GenreInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGenreInfo(PathMetadata metadata) {
        super(GenreInfo.class, metadata);
    }

}

