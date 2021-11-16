package com.upvote.aismpro.customrepository;

import com.upvote.aismpro.dto.NewLibrarySearchDTO;
import com.upvote.aismpro.entity.NewSong;

import java.io.Serializable;
import java.util.List;

public interface NewSongRepositoryCustom {

    public List<NewSong> findSongBySearchParamQD(NewLibrarySearchDTO newLibrarySearchDTO);
}
