package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.repository.CreateRepository;
import com.upvote.aismpro.repository.SongRepository;
import com.upvote.aismpro.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreateService {

    @Autowired
    private CreateRepository createRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private CustomModelMapper modelMapper;

    // 사용자가 생성한 음원 가져오기
    public List<SongDTO> getCreates(Long userId) throws Exception {
        try{
            List<SongDTO> creates = createRepository.findAllByUser_UserId(userId)
                    .stream()
                    .map(s -> modelMapper.toSongDTO().map(songRepository.getById(s.getSong().getSongId()), SongDTO.class))
                    .collect(Collectors.toList());
            return creates;
        } catch (Exception e) {
            throw new Exception("좋아요 없음.");
        }
    }

    // MyLibrary에서 생상한 음원 삭제
    public void deleteCreates(List<Long> deleteIds) throws Exception {
        Long userId = SecurityUtil.getCurrentUserId();
        try {
            deleteIds.stream().forEach(songId -> createRepository.deleteByUser_UserIdAndSong_SongId(userId, songId));
        }
        catch (Exception e) {
            throw new Exception();
        }
    }
}
