package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.entity.Like;
import com.upvote.aismpro.repository.LikeRepository;
import com.upvote.aismpro.repository.SongRepository;
import com.upvote.aismpro.repository.UserRepository;
import com.upvote.aismpro.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomModelMapper modelMapper;

    // 사용자가 좋아요 누른 음원 가져오기
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<SongDTO> getLikes(Long userId) throws Exception {
        try{
            List<SongDTO> likes = likeRepository.findAllByUser_UserId(userId)
                    .stream()
                    .map(s -> modelMapper.toSongDTO().map(songRepository.getById(s.getSong().getSongId()), SongDTO.class))
                    .collect(Collectors.toList());
            return likes;
        } catch (Exception e) {
            throw new Exception("좋아요 없음.");
        }
    }

    // MyLibrary에서 좋아요 삭제
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteLikes(List<Long> deleteIds) throws Exception {
        Long userId = SecurityUtil.getCurrentUserId();
        try {
            deleteIds.stream().forEach(songId -> likeRepository.deleteByUser_UserIdAndSong_SongId(userId, songId));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteLike(Long songId) throws Exception {
        Long userId = SecurityUtil.getCurrentUserId();
        try {
            likeRepository.deleteByUser_UserIdAndSong_SongId(userId, songId);
        } catch(Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void createLike(Long songId) throws Exception {
        Long userId = SecurityUtil.getCurrentUserId();
        try {

            Like like = Like.builder()
                    .user(userRepository.findById(userId).get())
                    .song(songRepository.findById(songId).get())
                    .build();

            // TODO 이미 있으면 에러 던지기
            likeRepository.save(like);
        } catch(Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }
}
