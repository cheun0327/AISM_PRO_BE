package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.entity.PlayListSong;

import java.util.List;

public interface PlaylistServiceInter {

    public void createPlaylistLike(String userId, String playlistId);

    public List<PlaylistDetailDTO> getSimilarPlaylist(MoodDTO moodDTO) throws Exception;
    public List<NewPlaylistDTO> getNewSimilarPlaylistPlaylist(PlaylistDetailDTO playlistDetailDTO) throws Exception;
    public List<NewPlaylistDTO> getNewSimilarPlaylist(NewSongDTO songDTO) throws Exception;

    public  List<NewPlaylistDTO> getSavedPlaylistBySongID(String songId) throws  Exception;

    public Integer getPlaylistLikeCnt(String playlistID);
}
