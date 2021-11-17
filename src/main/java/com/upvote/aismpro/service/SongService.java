package com.upvote.aismpro.service;

import com.google.api.client.util.Lists;
import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.LikeDTO;
import com.upvote.aismpro.dto.MoodDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.entity.Like;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.LikeRepository;
import com.upvote.aismpro.repository.SongRepository;
import com.upvote.aismpro.repository.UserRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.sql.Date;
import java.util.stream.Collectors;

@Service
public class SongService implements SongServiceInter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private CustomModelMapper modelMapper;

    @Override
    public String createSong(Map<String, Object> param) throws Exception {

        try {
            // 기본 필요 정보 세팅
            String userId = param.get("userId").toString();
            User user = userRepository.getById(userId);
            String songId = UUID.randomUUID().toString();

            String userPath = userId.replace("-","");
            String songPath = songId.replace("-", "") + ".wav";

            String filePath = "song/" + songPath;

            // song 객체 생성
            // 추후 songSaveDTO 생성 예정
            Song song = new Song(songId, param.get("songName").toString(), filePath,
                    param.get("genre").toString(), param.get("one").toString(), param.get("two").toString(), param.get("three").toString(),
                    "음원", param.get("length").toString(), user, new java.sql.Timestamp(new GregorianCalendar().getTimeInMillis()));

            // 생성 곡 저장 위치 디렉토리 확인
            String songDirPath = "./resource/"+userPath+"/song/";
            File songDir  = new File(songDirPath);
            if (!new File(songDirPath).exists()) songDir.mkdir();

            // 저장된 곡 위치 이동
            File source = new File("./resource/"+userPath+"/tmp/sample.wav");
            File target = new File(songDirPath + songPath);
            FileUtils.moveFile(source, target);

            // song 저장
            songRepository.save(song);

            return songId;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    @Override
    public SongDTO getSongDetail(String songId) {
        Optional<Song> songOpt = songRepository.findById(songId);
        Song song =  songOpt.orElseThrow(() -> new NoSuchElementException());
        return modelMapper.songMapper().map(song, SongDTO.class);
    }

    @Override
    public Integer getLikeCnt(String songId){
        Integer cnt = likeRepository.countBySong_SongId(songId);
        return cnt;
    }

    @Override
    public void creatLike(String userId, String songId) {
        Like like = new Like(userRepository.getById(userId), songRepository.getById(songId));
        likeRepository.save(like);
    }

    @Override
    public void deleteLike(String likeId) {
        likeRepository.deleteById(likeId);
    }

    @Override
    // 비슷한 곡 가져오기
    public List<SongDTO> getSimilarSong(MoodDTO moodDTO) {
        List<SongDTO> similar = songRepository.findSimilarSongQD(moodDTO)
                .stream()
                .map(s -> modelMapper.songMapper().map(s, SongDTO.class))
                .collect(Collectors.toList());
        Collections.shuffle(similar);
        if (similar.size() > 6) return Lists.newArrayList(similar.subList(0,6));
        return similar;
    }
}
