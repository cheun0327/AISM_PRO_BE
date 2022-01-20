package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.MyLibrarySearchDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.entity.Create;
import com.upvote.aismpro.entity.Like;
import com.upvote.aismpro.repository.CreateRepository;
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
@Transactional
public class CreateService {

    @Autowired
    private CreateRepository createRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomModelMapper modelMapper;

    // 사용자가 생성한 음원 가져오기
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<SongDTO> getCreates(Long userId) throws Exception {
        try{
            List<SongDTO> creates = createRepository.findAllByUser_UserIdOrderBySong_CreateDateDesc(userId)
                    .stream()
                    .map(s -> modelMapper.toSongDTO().map(songRepository.getById(s.getSong().getSongId()), SongDTO.class))
                    .collect(Collectors.toList());
            return creates;
        } catch (Exception e) {
            throw new Exception("생성 음원 없음.");
        }
    }

    // MyLibrary에서 생상한 음원 삭제
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteCreates(List<Long> deleteIds) throws Exception {
        Long userId = SecurityUtil.getCurrentUserId();
        System.out.println("userId : "+ userId);
        try {
            deleteIds.stream().forEach(songId -> createRepository.deleteByUser_UserIdAndSong_SongId(userId, songId));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    @Transactional
    public List<SongDTO> getMyLibrarySearchResult(MyLibrarySearchDTO myLibrarySearchDTO) throws Exception {
        Long userId = SecurityUtil.getCurrentUserId();

        try {
            List<SongDTO> result = createRepository.findMyLibraryCreateSearchQD(userId, myLibrarySearchDTO)
                    .stream()
                    .map(s -> modelMapper.toSongDTO().map(s, SongDTO.class))
                    .collect(Collectors.toList());

            return result;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Transactional
    public void saveSong(Long songId) throws Exception {
        try {
            createRepository.save(Create.builder()
                    .user(userRepository.findById(SecurityUtil.getCurrentUserId()).get())
                    .song(songRepository.findById(songId).get())
                    .build()
            );
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("create 저장 실패");
        }
    }
}
