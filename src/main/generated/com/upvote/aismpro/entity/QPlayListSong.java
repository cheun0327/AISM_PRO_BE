package com.upvote.aismpro.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPlayListSong is a Querydsl query type for PlayListSong
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPlayListSong extends EntityPathBase<PlayListSong> {

    private static final long serialVersionUID = -1533904877L;

    public static final QPlayListSong playListSong = new QPlayListSong("playListSong");

    public final StringPath playlistId = createString("playlistId");

    public final StringPath songId = createString("songId");

    public QPlayListSong(String variable) {
        super(PlayListSong.class, forVariable(variable));
    }

    public QPlayListSong(Path<? extends PlayListSong> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlayListSong(PathMetadata metadata) {
        super(PlayListSong.class, metadata);
    }

}

