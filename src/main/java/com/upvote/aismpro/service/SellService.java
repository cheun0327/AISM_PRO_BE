package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.MyLibrarySearchDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.entity.Sell;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.SellRepository;
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
public class SellService {

    @Autowired
    private SellRepository sellRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomModelMapper modelMapper;

    // 사용자가 판매하는 음원 가져오기
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<SongDTO> getSells(Long userId) throws Exception {
        try {
            List<SongDTO> sells = sellRepository.findAllByUser_UserId(userId)
                    .stream()
                    .map(s -> modelMapper.toSongDTO()
                            .map(songRepository.getById(s.getSong().getSongId()), SongDTO.class))
                    .collect(Collectors.toList());
            return sells;
        } catch (Exception e) {
            throw new Exception("좋아요 없음.");
        }
    }

    // MyLibrary에서 판매한 음원 삭제
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteSells(List<Long> deleteIds) throws Exception {
        Long userId = SecurityUtil.getCurrentUserId();
        try {
            deleteIds.stream()
                    .forEach(songId -> sellRepository.deleteByUser_UserIdAndSong_SongId(userId, songId));
        }
        catch (Exception e) {
            throw new Exception();
        }
    }

    @Transactional
    public void createSells(Long songId) throws Exception{

        User user = userRepository.getById(SecurityUtil.getCurrentUserId());

        Song song = songRepository.getById(songId);

        sellRepository.save(Sell.builder()
                .user(user)
                .song(song)
                .build());

    }

    public Boolean isSell(Long songId) {
         return !sellRepository.findAllBySong_SongId(songId).isEmpty();
    }

    @Transactional
    public List<SongDTO> getMyLibrarySearchResult(MyLibrarySearchDTO myLibrarySearchDTO) throws Exception {
        Long userId = SecurityUtil.getCurrentUserId();

        try {
            List<SongDTO> result = sellRepository.findMyLibrarySellSearchQD(userId, myLibrarySearchDTO)
                    .stream()
                    .map(s -> modelMapper.toSongDTO().map(s, SongDTO.class))
                    .collect(Collectors.toList());

            return result;
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
