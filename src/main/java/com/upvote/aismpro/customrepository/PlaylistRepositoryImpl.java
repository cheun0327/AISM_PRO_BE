package com.upvote.aismpro.customrepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.dto.PlaylistInfoDTO;
import com.upvote.aismpro.entity.QPlayList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlaylistRepositoryImpl implements PlaylistRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private QPlayList playList = QPlayList.playList;


//    public List<PlaylistInfoDTO> findInfoByCategoryAndPlaylistIdQD(@Param("ID") String ID){
//
//    }
}
