package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.repository.BuyRepository;
import com.upvote.aismpro.repository.SongRepository;
import com.upvote.aismpro.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuyService {

    @Autowired
    private BuyRepository buyRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private CustomModelMapper modelMapper;

    // 사용자가 구매한 음원 가져오기
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<SongDTO> getBuys(Long userId) throws Exception {
        try{
            List<SongDTO> buys = buyRepository.findAllByUser_UserId(userId)
                    .stream()
                    .map(s -> modelMapper.toSongDTO().map(songRepository.getById(s.getSong().getSongId()), SongDTO.class))
                    .collect(Collectors.toList());
            return buys;
        } catch (Exception e) {
            throw new Exception("구매 음원 없음.");
        }
    }

    // MyLibrary에서 구매 음원 삭제
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteBuys(List<Long> deleteIds) throws Exception {
        Long userId = SecurityUtil.getCurrentUserId();
        try {
            deleteIds.stream().forEach(songId -> buyRepository.deleteByUser_UserIdAndSong_SongId(userId, songId));
        }
        catch (Exception e) {
            throw new Exception();
        }
    }
}
