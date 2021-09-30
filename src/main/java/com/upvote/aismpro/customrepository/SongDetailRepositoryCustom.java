package com.upvote.aismpro.customrepository;

import com.upvote.aismpro.entity.SongDetail;

import java.util.List;

public interface SongDetailRepositoryCustom {
    //List<SongDetail> findBySearchParamQD(String type, String length, String genre, String mood1, String mood2);
    List<String> findSongIdBySearchParamQD(Integer type, Integer length, List<String> genre, List<String> mood1, List<String> mood2);
}
