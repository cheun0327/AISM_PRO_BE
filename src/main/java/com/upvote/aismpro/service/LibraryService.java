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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.stereotype.Service;

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
    private PlaylistLikeRepository playlistLikeRepository;
    @Autowired
    private GenreInfoRepository genreInfoRepository;
    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private CustomModelMapper modelMapper;

    // 라이브러리 검색 옵션
    public Map<String, List<String>> getSearchOptionDate() {
        Map<String, List<String>> map = new HashMap<>();

        map.put("genre", genreInfoRepository.findGenreQD());
        map.put("inst", getInstList());
        map.put("mood", getMoodList());
        map.put("playtime", new ArrayList<String>(Arrays.asList("30초", "1분", "1분 30초", "2분", "2분 30초", "3분", "3분 30초", "4분", "4분 30초", "5분")));
        map.put("tempo", new ArrayList<String>(Arrays.asList("80", "85", "90", "95", "100", "105", "110", "115", "120", "125", "130", "135", "140")));

        return map;
    }

    public Map<String, Object> getSearchResult(LibrarySearchDTO librarySearchDTO) throws Exception {
        Map<String, Object> map = new HashMap<>();

        try {
            // song type 있으면 플레이리스트 가져옴.
            List<PlaylistDTO> playlists = new ArrayList<>();
            if (!librarySearchDTO.getUserId().equals(-1L)) {
                playlists = getNewPlaylistsLike(librarySearchDTO.getType(), librarySearchDTO.getUserId());
            }
            else {
                playlists = getNewPlaylists(librarySearchDTO.getType());
            }
            map.put("playlist", playlists);

            // 검색 결과에 해당하는 song 리스트 가져옴
            List<Song> songList = songRepository.findSongBySearchParamQD(librarySearchDTO);

            // like 추가 & 형변환
            // 정렬 구현 안됨.
            List<SongDTO> songDTOList = new ArrayList<>();
            if (!librarySearchDTO.getUserId().equals(-1L)) {
                songDTOList = mapToSongDTOWithLike(songList, librarySearchDTO.getUserId());
            }
            else {
                songDTOList = mapToSongDTOWithoutLike(songList);
            }

            // seach 결과 필터링
            if (!Objects.equals(librarySearchDTO.getSearch(), "") && librarySearchDTO.getSearch() != null) {
                map.put("song", filterNewSearchKeyword(librarySearchDTO.getSearch(), songDTOList));
            }
            else {
                map.put("song", songDTOList);
            }

            // artist
            List<ArtistDTO> artists = new ArrayList<>();
            for (SongDTO ns : songDTOList) {
                User artist = songRepository.getById(ns.getSongId()).getUser();
                ArtistDTO artistDTO = new ArtistDTO(artist.getUserId(), artist.getNickname(), artist.getProfile());
                if (!artists.contains(artistDTO)) artists.add(new ArtistDTO(artist.getUserId(), artist.getNickname(), artist.getProfile()));
            }

            map.put("artist", artists);

            return map;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    private List<SongDTO> mapToSongDTOWithLike(List<Song> songList, Long userId) {
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

    private List<SongDTO> mapToSongDTOWithoutLike(List<Song> songList) {

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
        List<String> tmp = keywordRepository.findInstFromNewageQD();
        return tmp;
    }

    // 분위기 옵션 가져오기
    private List<String> getMoodList() {
        List<String> newageMood = keywordRepository.findMoodFromNewageQD();
        List<String> notNewageMood = keywordRepository.findMoodFromNotNewageQD();

        return Stream.concat(notNewageMood.stream(), newageMood.stream())
                .collect(Collectors.toList());
    }

    public List<PlaylistDTO> getNewPlaylists(String type) {
        if (type.equals("모두") || type.equals("음원")){
            List<PlaylistDTO> newPlaylistDTOList = new ArrayList<>();
            for (Playlist pl : playlistRepository.findAll()) {
                PlaylistDTO dto = modelMapper.toPlaylistDTO().map(pl, PlaylistDTO.class);
                dto.setPlaylistLike(false);
                newPlaylistDTOList.add(dto);
            }
            return newPlaylistDTOList;
        }

        return new ArrayList<>();
    }

    public List<PlaylistDTO> getNewPlaylistsLike(String type, Long userId) {
        List<Long> likes= playlistLikeRepository.findAllByUser(userRepository.getById(userId))
                .stream().map(src -> src.getPlaylist().getPlaylistId())
                .collect(Collectors.toList());

        if (type.equals("모두") || type.equals("음원")){
            List<PlaylistDTO> newPlaylistDTOList = new ArrayList<>();
            for (Playlist pl : playlistRepository.findAll()) {
                PlaylistDTO dto = modelMapper.toPlaylistDTO().map(pl, PlaylistDTO.class);
                dto.setPlaylistLike(likes.contains(pl.getPlaylistId()));
                newPlaylistDTOList.add(dto);
            }
            return newPlaylistDTOList;
        }

        return new ArrayList<>();
    }

    // song 검색 결과에서 검색 키워드 필터링
    List<SongDTO> filterNewSearchKeyword(String keyword, List<SongDTO> songDTOList) {
        List<SongDTO> filtered = new ArrayList<>();

        for (SongDTO ns : songDTOList) {
            String[] arr = {ns.getSongName(), ns.getCreatorName()};
            if (Arrays.stream(arr).anyMatch(keyword::equals) || ns.getTags().contains(keyword)) {
                filtered.add(ns);
            }
        }

        return filtered;
    }
}
