package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.entity.Playlist;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.*;
import com.upvote.aismpro.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class LibraryService {

    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;
    private final GenreInfoRepository genreInfoRepository;
    private final KeywordPathRepository keywordPathRepository;
    private final LikeRepository likeRepository;

    private final CustomModelMapper modelMapper;

    // 라이브러리 검색 옵션
    public Map<String, Object> getSearchOptionDate() {
        Map<String, Object> map = new HashMap<>();

        map.put("genre", genreInfoRepository.findGenreQD());
        map.put("inst", getInstList());
        map.put("mood", getMoodList());
        map.put("playtime", new ArrayList<String>(Arrays.asList("30초", "1분", "1분 30초", "2분", "2분 30초", "3분", "3분 30초", "4분", "4분 30초", "5분")));
        map.put("tempo", new ArrayList<String>(Arrays.asList("80", "85", "90", "95", "100", "105", "110", "115", "120", "125", "130", "135", "140")));

        return map;
    }

    // 라이브러리 검색 결괴
    public Map<String, Object> getSearchResult(LibrarySearchDTO librarySearchDTO) {
        Map<String, Object> map = new HashMap<>();
        // 플레이리스트 : 검색 + 15개 디폴트
        // 음원 : 검색 + 6개 디폴트
        // 아티스트 : 랜던 4개 디폴트

        List<Playlist> playlists = playlistRepository.findLibraryPlaylistSearchQD(librarySearchDTO.getSearch());
        if (playlists.isEmpty()) {
            playlists = playlistRepository.findAllRecentPlaylistQD();
        }
        map.put("playlist", playlists.stream()
                .map(PlaylistDetailDTO::new)
                .collect(Collectors.toList()));

        // 검색 결과에 해당하는 song 리스트 가져옴
        List<Song> songList = songRepository.findSongBySearchParamQD(
                librarySearchDTO.getSearch(), librarySearchDTO.getType(), librarySearchDTO.getSort(),
                librarySearchDTO.getGenre(), librarySearchDTO.getInst(), librarySearchDTO.getMood()
        );

        List<Long> currentUserLikes = likeRepository.findByUserIdAndSongIdIn(
                librarySearchDTO.getUserId(),
                songList.stream().map(Song::getSongId).collect(Collectors.toList())
        ).stream().map(like -> like.getSong().getSongId()).collect(Collectors.toList());

        // like 추가 & 형변환
        List<SongDTO> songDTOList = songList.stream()
                .map(SongDTO::new)
                .collect(Collectors.toList());

        songDTOList.forEach(dto -> {
            dto.setLike(currentUserLikes.contains(dto.getSongId()));
        });

        map.put("song", songDTOList);

        // 아티스트 "검색"
        List<ArtistDTO> artists = songRepository.findLibraryArtistSearchQD(librarySearchDTO.getSearch())
                .stream().map(usr -> modelMapper.toArtistDTO().map(usr, ArtistDTO.class))
                .collect(Collectors.toList());

        map.put("artist", artists);

        return map;
    }

    private List<SongDTO> mapToSongDTOWithLike(Page<Song> songList, Long userId) {
        User user = userRepository.getById(userId);
        List<Long> likes = user.getLikes().stream().map(l -> l.getSong().getSongId()).collect(Collectors.toList());

        List<SongDTO> newSongDTOs = new ArrayList<>();
        for (Song s : songList) {
            SongDTO nsdto = modelMapper.toSongDTO().map(s, SongDTO.class);
            nsdto.setLike(likes.contains(s.getSongId()));
            newSongDTOs.add(nsdto);
        }
        return newSongDTOs;
    }

    private List<SongDTO> mapToSongDTOWithoutLike(Page<Song> songList) {

        List<SongDTO> newSongDTOs = new ArrayList<>();
        for (Song s : songList) {
            SongDTO nsdto = modelMapper.toSongDTO().map(s, SongDTO.class);
            nsdto.setLike(false);
            newSongDTOs.add(nsdto);
        }
        return newSongDTOs;
    }

    // 악기 옵션 가져오기
    private List<String> getInstList() {
        List<String> tmp = keywordPathRepository.findInstFromNewageQD();
        return tmp;
    }

    // 분위기 옵션 가져오기
    private List<String> getMoodList() {
        return keywordPathRepository.findMoodFromNewageQD();
    }

//    public List<PlaylistDTO> getPlaylistsWithLike(Pageable pageable, String type, Long userId) {
//        List<Long> likes= playlistLikeRepository.findAllByUser(userRepository.getById(userId))
//                .stream().map(src -> src.getPlaylist().getPlaylistId())
//                .collect(Collectors.toList());
//
//        Page<Playlist> pls = playlistRepository.findAll(pageable);
//
//        if (type.equals("모두") || type.equals("음원")){
//            List<PlaylistDTO> newPlaylistDTOList = new ArrayList<>();
//            for (Playlist pl : playlistRepository.findAll()) {
//                PlaylistDTO dto = modelMapper.toPlaylistDTO().map(pl, PlaylistDTO.class);
//                dto.setPlaylistLike(likes.contains(pl.getPlaylistId()));
//                newPlaylistDTOList.add(dto);
//            }
//            return newPlaylistDTOList;
//        }
//
//        return new ArrayList<>();
//    }


    public List<PlaylistDTO> getPlaylistsWithoutLike(Pageable pageable, String type) {
        if (type.equals("모두") || type.equals("음원")) {
            List<PlaylistDTO> newPlaylistDTOList = new ArrayList<>();

            Page<Playlist> pls = playlistRepository.findAll(pageable);

            for (Playlist pl : playlistRepository.findAll()) {
                PlaylistDTO dto = modelMapper.toPlaylistDTO().map(pl, PlaylistDTO.class);
                newPlaylistDTOList.add(dto);
            }
            return newPlaylistDTOList;
        }

        return new ArrayList<>();
    }

    // song 전체보기
    public List<SongDTO> getTotalSongSearchResult(Pageable pageable, LibrarySearchDTO librarySearchDTO) {
        Long userId = SecurityUtil.getCurrentUserId();
        List<Song> songList = songRepository.findLibraryTotalSongSearchQD(pageable, librarySearchDTO);
        List<Long> currentUserLikedSongList = likeRepository.findByUserIdAndSongIdIn(
                        userId,
                        songList.stream().map(Song::getSongId).collect(Collectors.toList())
                ).stream()
                .map(like -> like.getSong().getSongId())
                .collect(Collectors.toList());

        return songList.stream()
                .map(SongDTO::new)
                .peek(songDTO -> songDTO.setLike(currentUserLikedSongList.contains(songDTO.getSongId())))
                .collect(Collectors.toList());
    }

    // playlist 전체 보기
    public List<PlaylistDetailDTO> getTotalPlaylistSearchResult(Pageable pageable, LibrarySearchDTO librarySearchDTO) {

        return playlistRepository.findLibraryTotalPlaylistSearchQD(pageable, librarySearchDTO)
                .stream()
                .map(pl -> modelMapper.toPlaylistDetailDTO().map(pl, PlaylistDetailDTO.class))
                .collect(Collectors.toList());

    }

    // artist 전체 보기
    public List<ArtistDTO> getTotalArtistSearchResult(Pageable pageable, LibrarySearchDTO librarySearchDTO) {

        return songRepository.findLibraryTotalArtistSearchQD(pageable, librarySearchDTO.getSearch())
                .stream()
                .map(a -> modelMapper.toPlaylistDTO().map(a, ArtistDTO.class))
                .collect(Collectors.toList());

    }

    // song 검색 결과에서 검색 키워드 필터링
    public List<SongDTO> filterNewSearchKeyword(String keyword, List<SongDTO> songDTOList) {
        List<SongDTO> filtered = new ArrayList<>();

        for (SongDTO ns : songDTOList) {
            String[] arr = {ns.getSongName(), ns.getCreatorName()};
            if (Arrays.stream(arr).anyMatch(keyword::contains) || ns.getTags().contains(keyword)) {
                filtered.add(ns);
            }
        }

        return filtered;
    }
}
