package com.upvote.aismpro.customrepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.dto.AlbumDTO;
import com.upvote.aismpro.entity.QAlbum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AlbumRepositoryImpl implements AlbumRepositoryCustom {
    private final JPAQueryFactory query;
    private final QAlbum album = QAlbum.album;

    @Override
    public List<AlbumDTO> findByUserID(String userID) {
    return (List<AlbumDTO>) query.from(album)
            .where (
                    album.user.id.eq(userID)
            )
            .fetch();
    }
}
