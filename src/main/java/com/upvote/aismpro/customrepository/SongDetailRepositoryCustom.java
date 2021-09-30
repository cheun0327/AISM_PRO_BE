package com.upvote.aismpro.customrepository;

import com.upvote.aismpro.entity.SongDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SongDetailRepositoryCustom {
    //List<SongDetail> findBySearchParamQD(String type, String length, String genre, String mood1, String mood2);
    List<String> findSongIdBySearchParamQD(String type, String length, List<String> genre, List<String> mood1, List<String> mood2);
}
