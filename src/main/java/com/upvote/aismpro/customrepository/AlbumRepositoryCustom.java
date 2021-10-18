package com.upvote.aismpro.customrepository;

import com.upvote.aismpro.dto.AlbumDTO;

import java.util.List;

public interface AlbumRepositoryCustom {
    public List<AlbumDTO> findByUserID(String userID);
}
