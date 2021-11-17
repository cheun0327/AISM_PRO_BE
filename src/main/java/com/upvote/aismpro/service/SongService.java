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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
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
    public void createSong(Map<String, Object> param) throws Exception {

        try {
            String wavPath = param.get("userId").toString().replace("-", "");

            // resource/userId/song/songId.wav
            String userId = param.get("userId").toString();
            User user = userRepository.getById(userId);
            String songId = UUID.randomUUID().toString();
            String filePath = userId.replace("-","") + "/song/" + songId.replace("-", "");

            Song song = new Song(songId, param.get("songName").toString(), filePath,
                    param.get("genre").toString(), param.get("one").toString(), param.get("two").toString(), param.get("three").toString(),
                    "음원", param.get("length").toString(), user, new java.sql.Timestamp(new GregorianCalendar().getTimeInMillis()));

            // 저장된 곡 위치 이동


            songRepository.save(song);
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
