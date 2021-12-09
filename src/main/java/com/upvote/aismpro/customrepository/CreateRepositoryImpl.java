package com.upvote.aismpro.customrepository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.dto.MyLibrarySearchDTO;
import com.upvote.aismpro.entity.QCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CreateRepositoryImpl implements  CreateRepositoryCustom{
    private final JPAQueryFactory query;
    private final QCreate create = QCreate.create;

//    @Override
//    public List<Long> findMyLibraryCreateSearchQD(Long userId, MyLibrarySearchDTO myLibrarySearchDTO) {
//        // TODO 정렬
//        String search = myLibrarySearchDTO.getSearch();
//
//        List<Long> ids = query.select(create.song.songId)
//                .from(create)
//                .where(
//                        create.song.songName.contains(search)
//                )
//    }
}
