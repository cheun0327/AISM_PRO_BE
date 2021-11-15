package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.entity.*;
import com.upvote.aismpro.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class LibraryService implements LibraryServiceInter{

    @Autowired
    private SongRepository songRepository;
    @Autowired
    private NewSongRepository newSongRepository;
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private OneTwoRepository oneTwoRepository;
    @Autowired
    private TwoThreeRepository twoThreeRepository;
    @Autowired
    private ThreeFourRepository threeFourRepository;
    @Autowired
    private FourFiveRepository fourFiveRepository;
    @Autowired
    private CustomModelMapper modelMapper;

    @Override
    public Map<String, List<String>> getRenderData() {
        Map<String, List<String>> map = new HashMap<>();

        // 뉴에이지 - three 컬럼 + 다른 장르 - two 컬럼(뉴에니지 세부 장르 제외)
        List<String> mood = Stream.concat(oneTwoRepository.findTwo().stream(),
                        twoThreeRepository.findThreeQD().stream())
                        .collect(Collectors.toList());

        map.put("genre", oneTwoRepository.findOneQD());
        map.put("inst", threeFourRepository.findFour());
        map.put("mood", mood);
        map.put("playtime", new ArrayList<String>(Arrays.asList("30초", "1분", "1분 30초", "2분", "2분 30초", "3분", "3분 30초", "4분", "4분 30초", "5분")));
        map.put("tempo", new ArrayList<String>(Arrays.asList("80", "85", "90", "95", "100", "105", "110", "115", "120", "125", "130", "135", "140")));
        return map;
    }


    public Map<String, Object> getNewSearch(NewLibrarySearchDTO librarySearchDTO) throws Exception {
        Map<String, Object> map = new HashMap<>();

        try {
            // song type 있으면 플레이리스트 가져옴.
            List<PlaylistDTO> playlists = getNewPlaylists(librarySearchDTO.getType());
            map.put("playlist", playlists);

            // song 가져옴
            List<NewSong> songList = newSongRepository.findSongBySearchParamQD(librarySearchDTO);

            // like 추가 & 형변환
            // 정렬 구현 안됨.
            List<NewSongDTO> songDTOList = new ArrayList<>();
            if (!Objects.equals(librarySearchDTO.getUserId(), "") && librarySearchDTO.getUserId() == null) {
                songDTOList = mapNewSong2NewSongDTOLike(songList, librarySearchDTO.getUserId());
            }
            else {
                songDTOList = mapNewSong2NewSongDTO(songList);
            }

            // seach 결과 필터링
            if (!Objects.equals(librarySearchDTO.getSearch(), "") && librarySearchDTO.getSearch() != null) {
                map.put("song", filterNewSearchKeyword(librarySearchDTO.getSearch(), songDTOList));
            }
            else {
                map.put("song", songDTOList);
            }

            return map;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    private List<NewSongDTO> mapNewSong2NewSongDTOLike(List<NewSong> songList, String userId) {
        User user = userRepository.getById(userId);
        List<String> likes = user.getLikes().stream().map(l -> l.getUser().getId()).collect(Collectors.toList());

        List<NewSongDTO> newSongDTOs = new ArrayList<>();
        for (NewSong s : songList) {
            NewSongDTO nsdto = modelMapper.newSongMapper().map(s, NewSongDTO.class);
            nsdto.setLike(likes.contains(s.getSongId()));
            newSongDTOs.add(nsdto);
        }
        return newSongDTOs;
    }

    private List<NewSongDTO> mapNewSong2NewSongDTO(List<NewSong> songList) {

        List<NewSongDTO> newSongDTOs = new ArrayList<>();
        for (NewSong s : songList) {
            NewSongDTO nsdto = modelMapper.newSongMapper().map(s, NewSongDTO.class);
            nsdto.setLike(false);
            newSongDTOs.add(nsdto);
        }
        return newSongDTOs;
    }

    @Override
    public Map<String, Object> getSearch(LibrarySearchDTO librarySearchDto) throws Exception {

        Map<String, Object> map = new HashMap<>();

        try{
            // song type 있으면 플레이리스트 가져옴.
            List<PlaylistDTO> playlists = getPlaylists(librarySearchDto.getType());
            map.put("playlist", playlists);

            // 필터 옵션으로 song 가져옴.
            List<Song> songList = songRepository.findSongBySearchParamQD(librarySearchDto);
            // song -> songDTO 매핑.
            List<SongDTO> songDTOList = songList.stream().map(s -> modelMapper.songMapper().map(s, SongDTO.class)).collect(Collectors.toList());

            Integer cnt = 0;
            for (SongDTO sd: songDTOList) {
                sd.setFileName("sample0" + String.valueOf(cnt % 5+1) + ".wav");
                sd.setThumbnail("sample0" + String.valueOf(cnt % 5+1) +".png");
                cnt++;
            }

            map.put("song", songDTOList);

            // 검색어 필터링
            if (!Objects.equals(librarySearchDto.getSearch(), "") && librarySearchDto.getSearch() != null) {
                map.put("song", filterSearchKeyword(librarySearchDto.getSearch(), songDTOList));
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new NoSuchElementException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }

        return map;
    }

    @Override
    public List<PlaylistDTO> getAllPlaylists() {
        return playlistRepository.findAll()
                .stream().map(pl -> modelMapper.playlistMapper().map(pl, PlaylistDTO.class))
                .collect(Collectors.toList());
    }


    // 검색으로 받은 DTO로 플레이리스트 정보 가져옴
    public List<PlaylistDTO> getPlaylists(List<String> types) {
        // 여기서 검색 keyword filtering
//        try {
//            if (types != null && types.contains("song")){
//                return playlistRepository.findAll()
//                        .stream().map(pl -> modelMapper.playlistMapper().map(pl, PlaylistDTO.class))
//                        .collect(Collectors.toList());
//            }
//
//            return new ArrayList<>();
//        } catch (Exception e) {
//            System.out.println("getPalyLists 에러");
//            e.printStackTrace();
//            throw new NoSuchElementException();
//        }
        if (types != null && types.contains("song")){
            return playlistRepository.findAll()
                    .stream().map(pl -> modelMapper.playlistMapper().map(pl, PlaylistDTO.class))
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    public List<PlaylistDTO> getNewPlaylists(String type) {
        if (type.equals("모두") || type.equals("음원")){
            return playlistRepository.findAll()
                    .stream().map(pl -> modelMapper.playlistMapper().map(pl, PlaylistDTO.class))
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    // 검색 키워드 필터링
    List<SongDTO> filterSearchKeyword(String keyword, List<SongDTO> songDTOList) {
        List<SongDTO> filtered = new ArrayList<>();

        for (SongDTO song : songDTOList) {
            String[] arr = {song.getSongName(), song.getCreatorName(), song.getSongId()};
            if (Arrays.stream(arr).anyMatch(keyword::equals)) {
                filtered.add(song);
                continue;
            }
            if (song.getTag().contains(keyword)) filtered.add(song);
        }
        return filtered;
    }

    List<NewSongDTO> filterNewSearchKeyword(String keyword, List<NewSongDTO> songDTOList) {
        List<NewSongDTO> filtered = new ArrayList<>();

        for (NewSongDTO ns : songDTOList) {
            String[] arr = {ns.getSongName(), ns.getCreatorName()};
            if (Arrays.stream(arr).anyMatch(keyword::equals) || ns.getTag().contains(keyword)) {
                filtered.add(ns);
            }
        }

        return filtered;
    }
}