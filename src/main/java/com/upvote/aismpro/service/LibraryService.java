package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.dto.SongBarDTO;
import com.upvote.aismpro.entity.PlayList;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.entity.SongDetail;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.PlaylistRepository;
import com.upvote.aismpro.repository.SongDetailRepository;
import com.upvote.aismpro.repository.SongRepository;
import com.upvote.aismpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LibraryService implements LibraryServiceInter{
    @Autowired
    private SongDetailRepository songDetailRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    // 라이브러리 페이지 검색 결과 반환
    public Map<String, Object> getSearchResult(LibrarySearchDTO librarySearchDto) {

        Map<String, Object> map = new HashMap<>();

        // 만약 "song" type 포함이면 플레이리스트도 가져옴
        List<PlayList> playlistList = getPlaylistList(librarySearchDto);
        map.put("playlist", playlistList);

        // 검색 입력 값으로 곡 상세 정보 가져오기
        List<SongDetail> songDetailList = songDetailRepository.findSongDetailBySearchParamQD(librarySearchDto);
        // songId로 기본 곡 정보 가져오기
        List<Song> songList = songRepository.findSongByIdListQD(songDetailList.stream().map(songDetail -> songDetail.getSongId()).collect(Collectors.toList()));
        // 기본 곡 정보와 상세 곡 정보로 SongBarDTO 생성
        List<SongBarDTO> songBarList = getSongBarList(songList, songDetailList);
        map.put("song", songBarList);

        return map;
    }

    // 검색으로 받은 DTO로 플레이리스트 정보 가져옴
    List<PlayList> getPlaylistList(LibrarySearchDTO librarySearchDto) {
        List<PlayList> playlistList = new ArrayList<>();

        if (librarySearchDto.getType().contains("song")){
            playlistList = playlistRepository.findAll();
        }

        return playlistList;
    }

    // 기본 곡 정보와 상세 곡 정보로 SongBarDTO 가공
    List<SongBarDTO> getSongBarList(List<Song> songList, List<SongDetail> songDetailList) {
        List<SongBarDTO> songBarList = new ArrayList<>();

        for (Song s : songList) {
            SongBarDTO tmp = new SongBarDTO();
            tmp.setSongId(s.getId());
            tmp.setSongName(s.getSongName());

            Optional<User> creator = userRepository.findById(s.getCreatorID());
            if (creator.isPresent())    tmp.setCreator(creator.get().getNickName());
            else    throw new EntityNotFoundException();

            tmp.setFileName(s.getFileName() + ".wav");
            tmp.setThumbnail("thisis/thumbnail.png");

            SongDetail sd =  songDetailList.stream().filter(SD -> s.getId().equals(SD.getSongId())).findFirst().get();
            List<String> tag = new ArrayList<>(Arrays.asList(new String[]{sd.getMood1(), sd.getMood2(), sd.getMood3()}));
            tmp.setTag(tag);

            songBarList.add(tmp);
        }
        return songBarList;
    }
}


