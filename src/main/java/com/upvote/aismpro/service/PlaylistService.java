package com.upvote.aismpro.service;

import com.google.api.client.util.Lists;
import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.LikeDTO;
import com.upvote.aismpro.dto.MoodDTO;
import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.dto.PlaylistDetailDTO;
import com.upvote.aismpro.entity.PlayList;
import com.upvote.aismpro.entity.PlaylistLike;
import com.upvote.aismpro.repository.PlaylistLikeRepository;
import com.upvote.aismpro.repository.PlaylistRepository;
import com.upvote.aismpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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
    private CustomModelMapper modelMapper;

    @Override
    public void createPlaylistLike(String userId, String playlistId) {
        playlistLikeRepository.save(
                new PlaylistLike(userRepository.getById(userId), playlistRepository.getById(playlistId))
        );
    }

    @Override
    public List<PlaylistDetailDTO> getSimilarPlaylist(MoodDTO moodDTO) throws Exception {
        List<PlayList> similar_li = playlistRepository.findByPlaylistIdNotAndFirstMoodOrSecondMoodOrThirdMood(moodDTO.getSongId(), moodDTO.getFirstMood(), moodDTO.getSecondMood(), moodDTO.getThirdMood());
        try {
            return similar_li.size() > 5 ?
                    similar_li
                            .stream().map(Playlist -> modelMapper.likeMapper().map(Playlist, PlaylistDetailDTO.class))
                            .collect(Collectors.toList())
                            .subList(0, 5)
                    :
                    similar_li
                            .stream().map(Playlist -> modelMapper.likeMapper().map(Playlist, PlaylistDetailDTO.class))
                            .collect(Collectors.toList());
        } catch(NoSuchElementException e) {
            e.printStackTrace();
            throw new NoSuchElementException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }

    }
}
