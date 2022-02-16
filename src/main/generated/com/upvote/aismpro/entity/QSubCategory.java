package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSubCategory is a Querydsl query type for SubCategory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSubCategory extends EntityPathBase<SubCategory> {

    private static final long serialVersionUID = -644919310L;

    public static final QSubCategory subCategory = new QSubCategory("subCategory");

    public final StringPath hashTag = createString("hashTag");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath keyword = createString("keyword");

    public QSubCategory(String variable) {
        super(SubCategory.class, forVariable(variable));
    }

    public QSubCategory(Path<? extends SubCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubCategory(PathMetadata metadata) {
        super(SubCategory.class, metadata);
    }

}

