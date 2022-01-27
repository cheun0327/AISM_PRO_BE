package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QOptionKeyword is a Querydsl query type for OptionKeyword
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOptionKeyword extends EntityPathBase<OptionKeyword> {

    private static final long serialVersionUID = -676558008L;

    public static final QOptionKeyword optionKeyword = new QOptionKeyword("optionKeyword");

    public final NumberPath<Long> keywordId = createNumber("keywordId", Long.class);

    public final StringPath keywordName = createString("keywordName");

    public QOptionKeyword(String variable) {
        super(OptionKeyword.class, forVariable(variable));
    }

    public QOptionKeyword(Path<? extends OptionKeyword> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOptionKeyword(PathMetadata metadata) {
        super(OptionKeyword.class, metadata);
    }

}

