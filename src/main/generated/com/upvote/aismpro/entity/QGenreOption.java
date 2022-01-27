package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QGenreOption is a Querydsl query type for GenreOption
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGenreOption extends EntityPathBase<GenreOption> {

    private static final long serialVersionUID = 887275628L;

    public static final QGenreOption genreOption = new QGenreOption("genreOption");

    public final NumberPath<Long> optionId = createNumber("optionId", Long.class);

    public final StringPath optionName = createString("optionName");

    public QGenreOption(String variable) {
        super(GenreOption.class, forVariable(variable));
    }

    public QGenreOption(Path<? extends GenreOption> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGenreOption(PathMetadata metadata) {
        super(GenreOption.class, metadata);
    }

}

