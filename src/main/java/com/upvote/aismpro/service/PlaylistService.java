package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.entity.PlayList;
import com.upvote.aismpro.entity.PlayListSong;
import com.upvote.aismpro.entity.PlaylistLike;
import com.upvote.aismpro.repository.PlaylistLikeRepository;
import com.upvote.aismpro.repository.PlaylistRepository;
import com.upvote.aismpro.repository.PlaylistSongRepository;
import com.upvote.aismpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PlaylistService implements PlaylistServiceInter{

    @Autowired
    private PlaylistLikeRepository playlistLikeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private PlaylistSongRepository playlistSongRepository;

    @Autowired
    private CustomModelMapper modelMapper;

    @Override
    public void createPlaylistLike(String userId, String playlistId) {
        playlistLikeRepository.save(
                new PlaylistLike(userRepository.getById(userId), playlistRepository.getById(playlistId))
        );
    }

    @Override
    public List<PlaylistDetailDTO> getSimilarPlaylist(MoodDTO moodDTO) throws Exception {
        List<PlayList> similar_li = playlistRepository.findSimilarPlaylistQD(moodDTO);
        try {
            return similar_li
                    .stream().map(Playlist -> modelMapper.playlistMapper().map(Playlist, PlaylistDetailDTO.class))
                    .collect(Collectors.toList());
        } catch(NoSuchElementException e) {
            e.printStackTrace();
            throw new NoSuchElementException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    @Override
    public List<NewPlaylistDTO> getNewSimilarPlaylistPlaylist(PlaylistDetailDTO playlistDetailDTO) throws Exception {
        List<PlayList> similar_li = playlistRepository.findSimilarPlaylistPlaylistQD(playlistDetailDTO);
        try {
            return similar_li
                    .stream().map(Playlist -> modelMapper.newPlaylistMapper().map(Playlist, NewPlaylistDTO.class))
                    .collect(Collectors.toList());
        } catch(NoSuchElementException e) {
            e.printStackTrace();
            throw new NoSuchElementException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    @Override
    public List<NewPlaylistDTO> getNewSimilarPlaylist(NewSongDTO songDTO) throws Exception {
        List<PlayList> similar_li = playlistRepository.findNewSimilarPlaylistQD(songDTO);
        try {
            return similar_li
                    .stream().map(Playlist -> modelMapper.newPlaylistMapper().map(Playlist, NewPlaylistDTO.class))
                    .collect(Collectors.toList());
        } catch(NoSuchElementException e) {
            e.printStackTrace();
            throw new NoSuchElementException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    @Override
    public  List<NewPlaylistDTO> getSavedPlaylistBySongID(String songId) throws  Exception {
        return playlistSongRepository.findSavedPlaylistBySongId(songId)
                .stream().map(playListSong -> modelMapper.newPlaylistMapper().map(playlistRepository.getById(playListSong.getPlaylistId()), NewPlaylistDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Integer getPlaylistLikeCnt(String playlistID) {
        return playlistLikeRepository.countPlaylistLikeByID(playlistID);
    }
}
