package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.PlayList;
import com.upvote.aismpro.entity.PlaylistLike;
import com.upvote.aismpro.repository.PlaylistLikeRepository;
import com.upvote.aismpro.repository.PlaylistRepository;
import com.upvote.aismpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaylistService implements PlaylistServiceInter{

    @Autowired
    private PlaylistLikeRepository playlistLikeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Override
    public void createPlaylistLike(String userId, String playlistId) {
        playlistLikeRepository.save(
                new PlaylistLike(userRepository.getById(userId), playlistRepository.getById(playlistId))
        );
    }
}
