package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOptionKeywordNode is a Querydsl query type for OptionKeywordNode
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOptionKeywordNode extends EntityPathBase<OptionKeywordNode> {

    private static final long serialVersionUID = -863319702L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOptionKeywordNode optionKeywordNode = new QOptionKeywordNode("optionKeywordNode");

    public final NumberPath<Integer> depth = createNumber("depth", Integer.class);

    public final QGenre genre;

    public final QOptionKeyword keyword;

    public final NumberPath<Long> nodeId = createNumber("nodeId", Long.class);

    public final QOptionKeywordNode parentNode;

    public final StringPath path = createString("path");

    public QOptionKeywordNode(String variable) {
        this(OptionKeywordNode.class, forVariable(variable), INITS);
    }

    public QOptionKeywordNode(Path<? extends OptionKeywordNode> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOptionKeywordNode(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOptionKeywordNode(PathMetadata metadata, PathInits inits) {
        this(OptionKeywordNode.class, metadata, inits);
    }

    public QOptionKeywordNode(Class<? extends OptionKeywordNode> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.genre = inits.isInitialized("genre") ? new QGenre(forProperty("genre")) : null;
        this.keyword = inits.isInitialized("keyword") ? new QOptionKeyword(forProperty("keyword")) : null;
        this.parentNode = inits.isInitialized("parentNode") ? new QOptionKeywordNode(forProperty("parentNode"), inits.get("parentNode")) : null;
    }

}

