package com.upvote.aismpro.service;

import com.google.api.client.util.Lists;
import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.dto.SimilarSrcDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.dto.SongTagDTO;
import com.upvote.aismpro.entity.Like;
import com.upvote.aismpro.entity.Playlist;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.repository.LikeRepository;
import com.upvote.aismpro.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SongService implements SongServiceInter{

    @Autowired
    private SongRepository songRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private CustomModelMapper modelMapper;

    // song detail 페이지에 뿌릴 상세 정보 리턴
    // like 어떻게 뿌려줄지 생각
    public SongDTO getSongDetail(Long songId) {
        Optional<Song> songOpt = songRepository.findBySongId(songId);
        Song song =  songOpt.orElseThrow(() -> new NoSuchElementException());
        return modelMapper.toSongDTO().map(song, SongDTO.class);
    }

    // 비슷한 곡 가져오기
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
    public List<SongDTO> getSimilarSongByTags(SongTagDTO songTagDTO) {
        List<SongDTO> similar = songRepository.findSimilarSongByTagsQD(songTagDTO)
                .stream()
                .map(s -> modelMapper.toSongDTO().map(s, SongDTO.class))
                .collect(Collectors.toList());
        Collections.shuffle(similar);
        if (similar.size() > 6) return Lists.newArrayList(similar.subList(0,6));
        return similar;
    }

    public Integer getLikeCnt(Long songId){
        Integer cnt = likeRepository.countBySong_SongId(songId);
        return cnt;
    }

    public SongDTO setLike2SongDTO(SongDTO songDTO, Long userId) throws Exception {
        try {
            List<Like> likes= likeRepository.findAllByUser_UserIdAndSong_SongId(userId, songDTO.getSongId());

            if(likes.size() == 1) songDTO.setLike(true);

            return songDTO;
        } catch (Exception e) {
            throw new Exception();
        }
    }

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
