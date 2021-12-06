package com.upvote.aismpro.service;

import com.google.api.client.util.Lists;
import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.dto.PlaylistDetailDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.dto.SongTagDTO;
import com.upvote.aismpro.entity.Playlist;
import com.upvote.aismpro.entity.PlaylistLike;
import com.upvote.aismpro.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PlaylistService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private PlaylistLikeRepository playlistLikeRepository;
    @Autowired
    private PlaylistSongRepository playlistSongRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private CustomModelMapper modelMapper;

    // user 별 play list 가져오기
    public List<PlaylistDTO> getPlayList(Long userId) throws Exception {
        try{
            List<Long> likes= playlistLikeRepository.findAllByUser(userRepository.getById(userId))
                    .stream().map(src -> src.getPlaylist().getPlaylistId())
                    .collect(Collectors.toList());

            // playlist like
            List<PlaylistDTO> playlistDTOList = new ArrayList<>();
            for (Playlist pl : playlistRepository.findAll()) {
                PlaylistDTO dto = modelMapper.toPlaylistDTO().map(pl, PlaylistDTO.class);
                dto.setPlaylistLike(likes.contains(pl.getPlaylistId()));
                playlistDTOList.add(dto);
            }
            return playlistDTOList;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new NoSuchElementException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    // playlist detail 가져오기
    public PlaylistDetailDTO getPlayListDetail(Long playlistId) throws Exception {
        try {
            return modelMapper.toPlaylistDetailDTO().map(playlistRepository.getById(playlistId), PlaylistDetailDTO.class);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new NoSuchElementException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }


    // playlistDetailDTO에 like 추가
    public PlaylistDetailDTO setLike2PlaylistDetailDTO(PlaylistDetailDTO pl, Long userId) throws Exception {
        try{
            List<PlaylistLike> playlistLikes = playlistLikeRepository.findAllByUser_UserIdAndPlaylist_PlaylistId(userId, pl.getPlaylistId());

            if(playlistLikes.size() == 1) pl.setPlaylistLike(true);

            return pl;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    // playlistDTO List에 like 추가
    public List<PlaylistDTO> setLike2PlaylistDTOList(List<PlaylistDTO> playlistDTOList, Long userId) throws Exception {
        try{
            List<Long> likes = playlistLikeRepository.findAllByUser_UserId(userId)
                    .stream()
                    .map(s -> s.getPlaylist().getPlaylistId())
                    .collect(Collectors.toList());

            for(PlaylistDTO pl : playlistDTOList) {
                pl.setPlaylistLike(likes.contains(pl.getPlaylistId()));
            }

            return playlistDTOList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    // 플레이리스트와 비슷한 플레이리스트 가져오기
    public List<PlaylistDTO> getSimilarPlaylist(Long playlistId) throws Exception {
        PlaylistDetailDTO pl = modelMapper.toPlaylistDetailDTO().map(playlistRepository.getById(playlistId), PlaylistDetailDTO.class);

        List<Playlist> similar_li = playlistRepository.findSimilarPlaylistQD(pl);
        try {
            return similar_li
                    .stream().map(Playlist -> modelMapper.toPlaylistDTO().map(Playlist, PlaylistDTO.class))
                    .collect(Collectors.toList());
        } catch(NoSuchElementException e) {
            e.printStackTrace();
            throw new NoSuchElementException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    // 음원과 비슷한 플레이리스트 가져오기
    public List<PlaylistDTO> getSimilarPlaylistBySong(Long songId) throws Exception {
        SongDTO songDTO = modelMapper.toSongDTO().map(songRepository.getById(songId), SongDTO.class);
        try {
            List<PlaylistDTO> similar = playlistRepository.findNewSimilarPlaylistQD(songDTO)
                    .stream().map(Playlist -> modelMapper.toPlaylistDTO().map(Playlist, PlaylistDTO.class))
                    .collect(Collectors.toList());

            Collections.shuffle(similar);
            if (similar.size() > 8) return Lists.newArrayList(similar.subList(0,8));
            return similar;

        } catch(NoSuchElementException e) {
            e.printStackTrace();
            throw new NoSuchElementException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    // 작곡하기 step2 비슷한 플레이리스트 가져오기
    public List<PlaylistDTO> getSimilarPlaylistByTags(SongTagDTO songTagDTO) throws Exception {
        try {
            List<PlaylistDTO> similar = playlistRepository.findNewSimilarPlaylistByTagsQD(songTagDTO)
                    .stream()
                    .map(pl -> modelMapper.toPlaylistDTO().map(pl, PlaylistDTO.class))
                    .collect(Collectors.toList());

            Collections.shuffle(similar);
            if (similar.size() > 8) return Lists.newArrayList(similar.subList(0,8));
            return similar;

        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    // 해당 음원이 저장된 플레이리스트 찾기
    public  List<PlaylistDTO> getSavedPlaylistBySongId(Long songId) throws  Exception {
        return playlistSongRepository.findPlaylistBySongIdQD(songId)
                .stream().map(playListSong -> modelMapper.toPlaylistDTO().map(playlistRepository.getById(playListSong.getPlaylistId()), PlaylistDTO.class))
                .collect(Collectors.toList());
    }

    public Integer getPlaylistLikeCnt(Long playlistId) {
        return playlistLikeRepository.countPlaylistLikeByID(playlistId);
    }

    public void createPlaylistLike(Long userId, Long playlistId) {
        playlistLikeRepository.save(
                new PlaylistLike(userRepository.getById(userId), playlistRepository.getById(playlistId))
        );
    }
}
