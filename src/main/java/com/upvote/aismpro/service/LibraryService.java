package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.ArtistDTO;
import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.entity.Playlist;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.*;
import com.upvote.aismpro.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LibraryService {

    @Autowired
    private SongRepository songRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private GenreInfoRepository genreInfoRepository;
    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private CustomModelMapper modelMapper;

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
    @Transactional
    public Map<String, Object> getSearchResult(LibrarySearchDTO librarySearchDTO) throws Exception {
        Map<String, Object> map = new HashMap<>();
        // 플레이리스트 : 검색 + 15개 디폴트
        // 음원 : 검색 + 6개 디폴트
        // 아티스트 : 랜던 4개 디폴트

        try {
            // song type 있으면 플레이리스트 가져옴. -> playlist like 적용 안한 버전
            List<PlaylistDTO> playlists = getLibrarySearchPlaylistResult(librarySearchDTO);
            map.put("playlist", playlists);

            // 검색 결과에 해당하는 song 리스트 가져옴
            Page<Song> songList = songRepository.findSongBySearchParamQD(librarySearchDTO);

            // like 추가 & 형변환
            // 정렬 구현 안됨.
            List<SongDTO> songDTOList = new ArrayList<>();
            if (!librarySearchDTO.getUserId().equals(-1L)) {
                songDTOList = mapToSongDTOWithLike(songList, librarySearchDTO.getUserId());
            }
            else {
                songDTOList = mapToSongDTOWithoutLike(songList);
            }

            // TODO List를 Page로 변경해줘야하나
            map.put("song", songDTOList);

            // 검색 결과 키워드 필터링
//            if (!Objects.equals(librarySearchDTO.getSearch(), "") && librarySearchDTO.getSearch() != null) {
//                map.put("song", filterNewSearchKeyword(librarySearchDTO.getSearch(), songDTOList));
//            }
//            else {
//                map.put("song", songDTOList);
//            }

            // TODO 아티스트 "검색"
            List<ArtistDTO> artists = songRepository.findLibraryArtistSearchQD(librarySearchDTO.getSearch())
                    .stream().map(usr -> modelMapper.toArtistDTO().map(usr, ArtistDTO.class))
                    .collect(Collectors.toList());

            map.put("artist", artists);

            return map;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    @Transactional
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

    @Transactional
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
    @Transactional
    private List<String> getInstList() {
        List<String> tmp = keywordRepository.findInstFromNewageQD();
        return tmp;
    }

    // 분위기 옵션 가져오기
    @Transactional
    private List<String> getMoodList() {
        List<String> newageMood = keywordRepository.findMoodFromNewageQD();
        List<String> notNewageMood = keywordRepository.findMoodFromNotNewageQD();

        return Stream.concat(notNewageMood.stream(), newageMood.stream())
                .collect(Collectors.toList());
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
        if (type.equals("모두") || type.equals("음원")){
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

    // Library 검색 결과 가져오기(랜더링)
    @Transactional
    public List<PlaylistDTO> getLibrarySearchPlaylistResult(LibrarySearchDTO librarySearchDTO) throws Exception {

        try {
            // 15개 제한해서 가져옴
            Page<Playlist> pls = playlistRepository.findLibraryPlaylistSearchQD(librarySearchDTO);
            return pls
                    .stream()
                    .map(pl -> modelMapper.toPlaylistDTO().map(pl, PlaylistDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    // song 전체보기
    @Transactional
    public List<SongDTO> getTotalSongSearchResult(Pageable pageable, LibrarySearchDTO librarySearchDTO) {
        Long userId = SecurityUtil.getCurrentUserId();
        Page<Song> songList = songRepository.findLibraryTotalSongSearchQD(pageable, librarySearchDTO);

        List<SongDTO> songDTOList = new ArrayList<>();
        if (!userId.equals(-1L)) {
            songDTOList = mapToSongDTOWithLike(songList, userId);
        }
        else {
            songDTOList = mapToSongDTOWithoutLike(songList);
        }
        System.out.println("total like add");
        return songDTOList;
    }

    // playlist 전체 보기
    @Transactional
    public List<PlaylistDTO> getTotalPlaylistSearchResult(Pageable pageable, LibrarySearchDTO librarySearchDTO) {

        return playlistRepository.findLibraryTotalPlaylistSearchQD(pageable, librarySearchDTO)
                .stream()
                .map(pl -> modelMapper.toPlaylistDTO().map(pl, PlaylistDTO.class))
                .collect(Collectors.toList());

    }

    // artist 전체 보기
    @Transactional
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
