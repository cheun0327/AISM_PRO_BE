package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.MyLibrarySearchDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.entity.Like;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.repository.LikeRepository;
import com.upvote.aismpro.repository.SongRepository;
import com.upvote.aismpro.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final SongRepository songRepository;
    private final CustomModelMapper modelMapper;

    // 사용자가 좋아요 누른 음원 가져오기
    @Transactional(readOnly = true)
    public List<SongDTO> getLikes(Long userId) throws Exception {
        try {
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
    public void deleteLikes(List<Long> deleteIds) throws Exception {
        Long userId = SecurityUtil.getCurrentUserId();
        try {
            deleteIds.forEach(songId -> likeRepository.deleteByUser_UserIdAndSong_SongId(userId, songId));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    public void deleteLike(Long songId) throws Exception {
        Long userId = SecurityUtil.getCurrentUserId();
        try {
            likeRepository.deleteByUser_UserIdAndSong_SongId(userId, songId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    public Long createLike(Long songId) {

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 노래를 찾을 수 없습니다."));

        Like like = Like.builder()
                .song(song)
                .build();

        return likeRepository.save(like).getId();
    }

    public List<SongDTO> getMyLibrarySearchResult(MyLibrarySearchDTO myLibrarySearchDTO) throws Exception {
        Long userId = SecurityUtil.getCurrentUserId();

        try {
            List<SongDTO> result = likeRepository.findMyLibraryLikeSearchQD(userId, myLibrarySearchDTO)
                    .stream()
                    .map(s -> modelMapper.toSongDTO().map(s, SongDTO.class))
                    .collect(Collectors.toList());

            return result;
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
