package com.upvote.aismpro.service;

import com.google.api.client.util.Lists;
import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.entity.Playlist;
import com.upvote.aismpro.entity.PlaylistSong;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.repository.PlaylistRepository;
import com.upvote.aismpro.repository.PlaylistSongRepository;
import com.upvote.aismpro.repository.SongRepository;
import com.upvote.aismpro.repository.UserRepository;
import com.upvote.aismpro.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class PlaylistService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;
    private final PlaylistSongRepository playlistSongRepository;
    private final SongRepository songRepository;
    private final CustomModelMapper modelMapper;

    public Long createPlaylist(PlaylistSaveDTO playlistSaveDTO, MultipartFile file) throws Exception {
        try {
            Playlist playlist = modelMapper.playlistSaveDTO2playlist().map(playlistSaveDTO, Playlist.class);

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
                playlistRepository.save(savedPlaylist);
            }

            return savedPlaylist.getPlaylistId();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    // user 별 play list 가져오기
    public List<PlaylistDTO> getPlayList(Long userId) throws Exception {
        try {
            // playlist like
            List<PlaylistDTO> playlistDTOList = new ArrayList<>();
            for (Playlist pl : playlistRepository.findAll()) {
                PlaylistDTO dto = modelMapper.toPlaylistDTO().map(pl, PlaylistDTO.class);
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

    public List<PlaylistDTO> getUserPlaylist(Long userId) throws Exception {
        try {
            List<PlaylistDTO> playlistDTOList = playlistRepository.findMyLibraryAllPlaylistQD(userId)
                    .stream().map(pl -> modelMapper.toPlaylistDTO().map(pl, PlaylistDTO.class))
                    .collect(Collectors.toList());
            return playlistDTOList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    // playlist detail 가져오기
    public PlaylistDetailDTO getPlayListDetail(Long playlistId) throws Exception {
        try {
            Playlist playlist = playlistRepository.findByIdFetchSongQD(playlistId);
            List<Song> songs = playlist.getSongs();

            List<String> firstKeywords = songs.stream().map(Song::getTwo).collect(Collectors.toList());
            List<String> secondKeywords = songs.stream().map(Song::getThree).collect(Collectors.toList());
            List<String> thirdKeywords = songs.stream().map(Song::getFour).collect(Collectors.toList());

            List<String> keywords = Arrays.asList(
                    userService.getMostFrequentTags(firstKeywords, 1),
                    userService.getMostFrequentTags(secondKeywords, 1),
                    userService.getMostFrequentTags(thirdKeywords, 1)
            );

            PlaylistDetailDTO dto = modelMapper.toPlaylistDetailDTO().map(playlist, PlaylistDetailDTO.class);
            dto.setKeywords(keywords);

            return dto;
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
        } catch (NoSuchElementException e) {
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
            if (similar.size() > 8) return Lists.newArrayList(similar.subList(0, 8));
            return similar;

        } catch (NoSuchElementException e) {
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
            if (similar.size() > 8) return Lists.newArrayList(similar.subList(0, 8));
            return similar;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    // 해당 음원이 저장된 플레이리스트 찾기
    public List<PlaylistDTO> getSavedPlaylistBySongId(Long songId) throws Exception {
        List<PlaylistDTO> savedPlaylists = playlistSongRepository.findPlaylistBySongIdQD(songId)
                .stream().map(playListSong -> modelMapper.toPlaylistDTO().map(playlistRepository.getById(playListSong.getPlaylistId()), PlaylistDTO.class))
                .collect(Collectors.toList());

        if (savedPlaylists.isEmpty()) throw new NoSuchElementException();

        return savedPlaylists;
    }

    // MyLibrary 검색 결과 가져오기
    public List<PlaylistDTO> getMyLibrarySearchResult(MyLibrarySearchDTO myLibrarySearchDTO) throws Exception {
        Long userId = SecurityUtil.getCurrentUserId();
        try {
            // TODO 검색어 포함된 플레이리스트 찾기 - 플레이리스트명, 키워드 123,
            List<PlaylistDTO> result = playlistRepository.findMyLibraryPlaylistSearchQD(userId, myLibrarySearchDTO)
                    .stream().map(pl -> modelMapper.toPlaylistDTO().map(pl, PlaylistDTO.class))
                    .collect(Collectors.toList());


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

    public ResponseEntity<SongListForAddToPlaylistDTO> getSongListAddToPlaylist() {
        // userId 가져오기
        Long userId = SecurityUtil.getCurrentUserId();

        // 유저가 작곡한 곡이 3곡 이상인지 확인
        boolean isEnough = songRepository.isEnoughAddToPlaylistQD(userId);

        // 3곡 이상이면 해당 유저가 작곡한 곡 중 랜덤 3곡
        // 3곡 미만이면 다른 유저가 작곡한 전체 곡 중 랜덤 3곡
        // song -> songDTO 변환
        List<SongDTO> songDTOList = songRepository.findSongListByUserIdLimit3QD(isEnough ? userId : null).stream()
                .map(song -> modelMapper.toSongDTO().map(song, SongDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new SongListForAddToPlaylistDTO(isEnough, songDTOList));
    }

    public ResponseEntity<SaveSongListResDTO> addSongList(SaveSongListReqDTO reqDTO) {

        Long playlistId = reqDTO.getPlaylistId();

        // 해당 플레이리스트에 곡이 추가되었는지 중복체크
        List<PlaylistSong> duplicateList = playlistSongRepository.findSavedSongListQD(reqDTO.getPlaylistId(), reqDTO.getSongIdList());
        List<Song> songList = songRepository.findAllByIdFetchUserQD(reqDTO.getSongIdList());

        List<PlaylistSong> playlistSongList = reqDTO.getSongIdList().stream()
                .filter(songId -> duplicateList.stream()
                        .noneMatch(playlistSong -> playlistSong.getSongId().equals(songId))
                )
                .map(songId -> new PlaylistSong(playlistId, songId))
                .collect(Collectors.toList());

        playlistSongRepository.saveAll(playlistSongList);

        SaveSongListResDTO resDTO = new SaveSongListResDTO(
                // 중복돼서 저장하지 않은 곡 DTO 리스트
                songList.stream()
                        .filter(song -> duplicateList.stream()
                                .anyMatch(playlistSong -> playlistSong.getSongId().equals(song.getSongId())))
                        .map(song -> modelMapper.toSongDTO().map(song, SongDTO.class))
                        .collect(Collectors.toList()),

                // 저장한 곡 DTO 리스트
                songList.stream()
                        .filter(song -> duplicateList.stream()
                                .noneMatch(playlistSong -> playlistSong.getSongId().equals(song.getSongId())))
                        .map(song -> modelMapper.toSongDTO().map(song, SongDTO.class))
                        .collect(Collectors.toList())
        );

        return ResponseEntity.ok(resDTO);
    }
}
