package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.MyLibrarySearchDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.entity.Buy;
import com.upvote.aismpro.entity.Sell;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.BuyRepository;
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
public class BuyService {

    @Autowired
    private BuyRepository buyRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CreditService creditService;
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

    @Transactional
    public void createBuys(Long songId) throws Exception {
        User user = userRepository.getById(SecurityUtil.getCurrentUserId());

        Song song = songRepository.getById(songId);

        buyRepository.save(Buy.builder()
                .user(user)
                .song(song)
                .build());

        creditService.subtractCredit(10000L);

    }

    public Boolean isBuy(Long songId) {
        return !buyRepository.findAllByUser_UserIdAndSong_SongId(SecurityUtil.getCurrentUserId(), songId).isEmpty();
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

    @Transactional
    public List<SongDTO> getMyLibrarySearchResult(MyLibrarySearchDTO myLibrarySearchDTO) throws Exception {
        Long userId = SecurityUtil.getCurrentUserId();

        try {
            List<SongDTO> result = buyRepository.findMyLibraryBuySearchQD(userId, myLibrarySearchDTO)
                    .stream()
                    .map(s -> modelMapper.toSongDTO().map(s, SongDTO.class))
                    .collect(Collectors.toList());

            return result;
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
