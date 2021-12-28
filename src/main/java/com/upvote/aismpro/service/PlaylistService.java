package com.upvote.aismpro.service;

import com.google.api.client.util.Lists;
import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.entity.Playlist;
import com.upvote.aismpro.repository.*;
import com.upvote.aismpro.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    private PlaylistSongRepository playlistSongRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private CustomModelMapper modelMapper;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void createPlaylist(PlaylistSaveDTO playlistSaveDTO, MultipartFile file) throws Exception {
        Long userId = SecurityUtil.getCurrentUserId();

        try {
            Playlist playlist = modelMapper.playlistSaveDTO2playlist().map(playlistSaveDTO, Playlist.class);
            playlist.setUser(userRepository.getById(userId));
            System.out.println(playlistSaveDTO);
            System.out.println(playlist.getName());
            System.out.println(playlist.getPlaylistId());

            // playlist 정보 저장
            Playlist savedPlaylist = playlistRepository.save(playlist);
            // TODO 디폴트 플레이리스트 제목 여부

            // playlist img 저장
            // null로 들어오면 그냥 아무 처리도 안하고 리턴 줄때 만들어서 주기
            if (file != null) {
                String dirPath = "/var/lib/jenkins/workspace/img/playlist";
                String[] imgNameArr = file.getOriginalFilename().split("\\.");
                String imgName = savedPlaylist.getPlaylistId() + "." + imgNameArr[imgNameArr.length - 1];
                file.transferTo(new File(dirPath + "/" + imgName));
                savedPlaylist.setImgFile(imgName);
            }
            playlistRepository.save(savedPlaylist);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    // user 별 play list 가져오기
    public List<PlaylistDTO> getPlayList(Long userId) throws Exception {
        try{
//            List<Long> likes= playlistLikeRepository.findAllByUser(userRepository.getById(userId))
//                    .stream().map(src -> src.getPlaylist().getPlaylistId())
//                    .collect(Collectors.toList());

            // playlist like
            List<PlaylistDTO> playlistDTOList = new ArrayList<>();
            for (Playlist pl : playlistRepository.findAll()) {
                PlaylistDTO dto = modelMapper.toPlaylistDTO().map(pl, PlaylistDTO.class);
//                dto.setPlaylistLike(likes.contains(pl.getPlaylistId()));
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

//    // playlistDetailDTO에 like 추가
//    public PlaylistDetailDTO setLike2PlaylistDetailDTO(PlaylistDetailDTO pl, Long userId) throws Exception {
//        try{
//            List<PlaylistLike> playlistLikes = playlistLikeRepository.findAllByUser_UserIdAndPlaylist_PlaylistId(userId, pl.getPlaylistId());
//
//            if(playlistLikes.size() == 1) pl.setPlaylistLike(true);
//
//            return pl;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Exception();
//        }
//    }
//
//    // playlistDTO List에 like 추가
//    public List<PlaylistDTO> setLike2PlaylistDTOList(List<PlaylistDTO> playlistDTOList, Long userId) throws Exception {
//        try{
//            List<Long> likes = playlistLikeRepository.findAllByUser_UserId(userId)
//                    .stream()
//                    .map(s -> s.getPlaylist().getPlaylistId())
//                    .collect(Collectors.toList());
//            System.out.println("플레이리스트 라이크 세팅");
//            System.out.println(likes);
//            for(PlaylistDTO pl : playlistDTOList) {
//                pl.setPlaylistLike(likes.contains(pl.getPlaylistId()));
//            }
//
//            return playlistDTOList;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Exception();
//        }
//    }

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

    // MyLibrary 검색 결과 가져오기
    public List<PlaylistDTO> getMyLibrarySearchResult(MyLibrarySearchDTO myLibrarySearchDTO) throws Exception {
        Long userId = SecurityUtil.getCurrentUserId();
        try {
            // TODO 검색어 포함된 플레이리스트 찾기 - 플레이리스트명, 키워드 123,
            List<PlaylistDTO> result =  playlistRepository.findMyLibraryPlaylistSearchQD(userId, myLibrarySearchDTO)
                    .stream().map(pl -> modelMapper.toPlaylistDTO().map(pl, PlaylistDTO.class))
                    .collect(Collectors.toList());

            Collections.shuffle(result);
            if (result.size() > 8) {
                result = Lists.newArrayList(result.subList(0,8));
            }

//            // like 추가
//            result = setLike2PlaylistDTOList(result, userId);
//            System.out.println(result);

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }


//    public Integer getPlaylistLikeCnt(Long playlistId) {
//        return playlistLikeRepository.countPlaylistLikeByID(playlistId);
//    }
//
//    public void createPlaylistLike(Long userId, Long playlistId) {
//        playlistLikeRepository.save(
//                new PlaylistLike(userRepository.getById(userId), playlistRepository.getById(playlistId))
//        );
//    }

    public Boolean validPlaylistName(String playlistName) throws Exception {
        try {
            List<Playlist> sameNamePls = playlistRepository.findAllByName(playlistName);
            if (sameNamePls.isEmpty()) return true;
            throw new Exception("플레이리스트 이름 중복");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }
}
