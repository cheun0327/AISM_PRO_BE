package com.upvote.aismpro.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.util.Lists;
import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.entity.Like;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.repository.LikeRepository;
import com.upvote.aismpro.repository.SongRepository;
import com.upvote.aismpro.repository.UserRepository;
import com.upvote.aismpro.security.SecurityUtil;
import com.upvote.aismpro.vo.SongSaveVO;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SongService implements SongServiceInter{

    @Autowired
    private SongRepository songRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomModelMapper modelMapper;


    // 생성 sond 저장
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public SongDTO saveSong(SongSaveDTO songSave, MultipartFile file) throws Exception {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Long userId = SecurityUtil.getCurrentUserId();

        try {
            Song song = modelMapper.songSaveDTO2song().map(songSave, Song.class);
            song.setUser(userRepository.getById(userId));

            // song 정보 저장
            Song savedSong = songRepository.save(song);
            if (savedSong.getSongName().equals("음원 제목")) savedSong.setSongName("Song" + savedSong.getSongId());

            // song img 저장
            if (file != null) {
                  String dirPath = "/var/lib/jenkins/workspace/img/song";
//                String dirPath = "/Users/upvote3/Desktop/springTest/img/song";
                String[] imgNameArr = file.getOriginalFilename().split("\\.");
                String imgName = savedSong.getSongId() + "." + imgNameArr[imgNameArr.length - 1];
                file.transferTo(new File(dirPath + "/" + imgName));
                savedSong.setImgFile(imgName);
            };
            songRepository.save(savedSong);

            // songDTO
            SongDTO songDTO = modelMapper.toSongDTO().map(savedSong, SongDTO.class);
            System.out.println(songDTO);

            return songDTO;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    // song 삭제
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteSong(Long songId) {
        songRepository.deleteById(songId);
    }

    public void moveSongWavFile(Long songId) throws IOException {
        Long userId = SecurityUtil.getCurrentUserId();
        String dirPath = "/var/lib/jenkins/workspace/song";
//        String dirPath = "/Users/upvote3/Desktop/springTest/song";

        // 생성 곡 저장 위치 디렉토리 확인
        String songDirPath = dirPath + "/" + userId + "/tmp/" + userId + ".mp3";
//        File songDir  = new File(songDirPath);
//        if (!new File(songDirPath).exists()) songDir.mkdir();

        // 저장된 곡 위치 이동
        File source = new File(songDirPath);
        File target = new File(dirPath + "/" + songId + ".mp3");
        FileUtils.moveFile(source, target);
    }

    // song detail 페이지에 뿌릴 상세 정보 리턴
    // like 어떻게 뿌려줄지 생각
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public SongDTO getSongDetail(Long songId) {
        Optional<Song> songOpt = songRepository.findBySongId(songId);
        Song song =  songOpt.orElseThrow(() -> new NoSuchElementException());
        return modelMapper.toSongDTO().map(song, SongDTO.class);
    }

    // 비슷한 곡 가져오기
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<SongDTO> getSimilarSong(Long songId) {
        Song song = songRepository.getById(songId);
        List<SongDTO> similar = songRepository.findSimilarSongQD(song)
                .stream()
                .map(s -> modelMapper.toSongDTO().map(s, SongDTO.class))
                .collect(Collectors.toList());
        Collections.shuffle(similar);
        if (similar.size() > 6) return Lists.newArrayList(similar.subList(0,6));
        return similar;
    }

    // 작곡하기 step2 비슷한 곡 가져오기
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<SongDTO> getSimilarSongByTags(SongTagDTO songTagDTO) {
        List<SongDTO> similar = songRepository.findSimilarSongByTagsQD(songTagDTO)
                .stream()
                .map(s -> modelMapper.toSongDTO().map(s, SongDTO.class))
                .collect(Collectors.toList());
        Collections.shuffle(similar);
        if (similar.size() > 6) return Lists.newArrayList(similar.subList(0,6));
        return similar;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Integer getLikeCnt(Long songId){
        Integer cnt = likeRepository.countBySong_SongId(songId);
        return cnt;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public SongDTO setLike2SongDTO(SongDTO songDTO, Long userId) throws Exception {
        try {
            List<Like> likes= likeRepository.findAllByUser_UserIdAndSong_SongId(userId, songDTO.getSongId());

            if(likes.size() == 1) songDTO.setLike(true);

            return songDTO;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<SongDTO> setLike2SongDTOList(List<SongDTO> songDTOList, Long userId) throws Exception {
        try {
            List<Long> likes = likeRepository.findAllByUser_UserId(userId)
                    .stream()
                    .map(s -> s.getSong().getSongId())
                    .collect(Collectors.toList());

            for (SongDTO s : songDTOList) {
                s.setLike(likes.contains(s.getSongId()));
            }

            return songDTOList;
        } catch (Exception e) {
            throw new Exception();
        }
    }

}
