package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1604385207L;

    public static final QUser user = new QUser("user");

    public final ListPath<Buy, QBuy> buys = this.<Buy, QBuy>createList("buys", Buy.class, QBuy.class, PathInits.DIRECT2);

    public final ListPath<Create, QCreate> creates = this.<Create, QCreate>createList("creates", Create.class, QCreate.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final StringPath id = createString("id");

    public final ListPath<Like, QLike> likes = this.<Like, QLike>createList("likes", Like.class, QLike.class, PathInits.DIRECT2);

    public final StringPath nickName = createString("nickName");

    public final StringPath platform = createString("platform");

    public final ListPath<PlayList, QPlayList> playlists = this.<PlayList, QPlayList>createList("playlists", PlayList.class, QPlayList.class, PathInits.DIRECT2);

    public final StringPath profile = createString("profile");

    public final ListPath<Sell, QSell> sells = this.<Sell, QSell>createList("sells", Sell.class, QSell.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

