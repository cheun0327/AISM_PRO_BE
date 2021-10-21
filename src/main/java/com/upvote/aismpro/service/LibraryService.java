package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.entity.PlayList;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.repository.PlaylistRepository;
import com.upvote.aismpro.repository.SongRepository;
import com.upvote.aismpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class LibraryService implements LibraryServiceInter{

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomModelMapper modelMapper;

    @Override
    public Map<String, Object> getSearch(LibrarySearchDTO librarySearchDto) {

        Map<String, Object> map = new HashMap<>();

        List<PlaylistDTO> playlists = getPlaylists(librarySearchDto.getType());
        map.put("playlist", playlists);

        List<Song> songList = songRepository.findSongBySearchParamQD(librarySearchDto);
        List<SongDTO> songDTOList = songList.stream().map(s -> modelMapper.songMapper().map(s, SongDTO.class)).collect(Collectors.toList());

        Integer cnt = 0;
        for (SongDTO sd: songDTOList) {
            sd.setFileName("sample0" + String.valueOf(cnt % 5+1) + ".wav");
            sd.setThumbnail("sample0" + String.valueOf(cnt % 5+1) +".png");
            cnt++;
        }

        map.put("song", songDTOList);

        if (!Objects.equals(librarySearchDto.getSearch(), "") && librarySearchDto.getSearch() != null) {
            map.put("song", filterSearchKeyword(librarySearchDto.getSearch(), songDTOList));
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

        if (types.contains("song")){
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
}