package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.MoodDTO;
import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.dto.PlaylistDetailDTO;
import com.upvote.aismpro.entity.PlayListSong;

import java.util.List;

public interface PlaylistServiceInter {

    public void createPlaylistLike(String userId, String playlistId);

    public List<PlaylistDetailDTO> getSimilarPlaylist(MoodDTO moodDTO) throws Exception;

    public  List<PlaylistDetailDTO> getSavedPlaylistBySongID(String songId) throws  Exception;

    public Integer getPlaylistLikeCnt(String playlistID);
}
