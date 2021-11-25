package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.dto.PlaylistDetailDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.entity.Playlist;
import com.upvote.aismpro.entity.PlaylistLike;
import com.upvote.aismpro.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        List<Playlist> similar_li = playlistRepository.findNewSimilarPlaylistQD(songDTO);
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
