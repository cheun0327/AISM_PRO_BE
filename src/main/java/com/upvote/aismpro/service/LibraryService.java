package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.dto.PlaylistInfoDTO;
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
        // 검색 키워드 필터링
        if (librarySearchDto.getSearch() != "" && librarySearchDto.getSearch() != null) {
            System.out.println("키워드 검색 : " + librarySearchDto.getSearch());
            map.put("song", filterSearchKeyword(librarySearchDto.getSearch(), songBarList));
        }
        else map.put("song", songBarList);

        return map;
    }

    // 검색 키워드 필터링
    List<SongBarDTO> filterSearchKeyword(String keyword, List<SongBarDTO> songBarList) {
        List<SongBarDTO> filtered = new ArrayList<>();

        for (SongBarDTO songBar : songBarList) {
            String[] arr = {songBar.getSongName(), songBar.getCreator(), songBar.getSongId()};
            if (Arrays.stream(arr).anyMatch(keyword::equals)) {
                filtered.add(songBar);
                continue;
            }
            if (songBar.getTag().contains(keyword)) filtered.add(songBar);
        }
        return filtered;
    }

    @Override
    // View Detail Playlist
    public List<PlaylistInfoDTO> getPlaylistInfo(String category, String id) {
        List<PlaylistInfoDTO> playlistInfoDTO_li = new ArrayList<>();

        PlayList playlist = playlistRepository.getById(id);

        for (Song plSong : playlist.getSongs()) {
            PlaylistInfoDTO playlistInfoDTO = new PlaylistInfoDTO(
                    playlist.getUser().getNickName(), playlist.getName(),
                    playlist.getState(), playlist.getImg(),
                    plSong.getCreateDate(), "작곡가 아이디",
                    plSong.getSongName(), plSong.getFileName()
            );
            playlistInfoDTO.setSongCreatorName("작곡가 이름");
            playlistInfoDTO_li.add(playlistInfoDTO);
        }
        return playlistInfoDTO_li;
    }

    // 검색으로 받은 DTO로 플레이리스트 정보 가져옴
    List<PlayList> getPlaylistList(LibrarySearchDTO librarySearchDto) {
        // 여기서 검색 keyword filtering
        List<PlayList> playlistList = new ArrayList<>();

        if (librarySearchDto.getType().contains("song")){
            playlistList = playlistRepository.findAll();
        }
//        List<PlaylistInfoDTO> playlistInfoDTOList = new ArrayList<>();
//
//        for (PlayList playList : playlistList) {
//            for (Song song : playList.getSongs()) {
//                PlaylistInfoDTO dto = new PlaylistInfoDTO(
//                        playList.getUser().getNickName(), playList.getName(),
//                        playList.getState(), playList.getImg(),
//                        song.getCreateDate(), song.getUser().getId(),
//                        song.getSongName(), song.getFileName()
//                );
//
//            }
//        }

        return playlistList;
    }

    // 기본 곡 정보와 상세 곡 정보로 SongBarDTO 가공
    List<SongBarDTO> getSongBarList(List<Song> songList, List<SongDetail> songDetailList) {
        List<SongBarDTO> songBarList = new ArrayList<>();

        Integer cnt = 0;
        for (Song s : songList) {
            SongBarDTO tmp = new SongBarDTO();
            tmp.setSongId(s.getSongId());
            tmp.setSongName(s.getSongName());

            User creator = s.getUser();

            if (creator != null)    tmp.setCreator(creator.getNickName());
            else                    tmp.setCreator("작곡가 없음");

            tmp.setFileName("sample0" + String.valueOf(cnt % 5+1) + ".wav");
            tmp.setThumbnail("sample0" + String.valueOf(cnt % 5+1) +".png");

            SongDetail sd =  songDetailList.stream().filter(SD -> s.getSongId().equals(SD.getSongId())).findFirst().get();
            List<String> tag = new ArrayList<>(Arrays.asList(new String[]{sd.getMood1(), sd.getMood2(), sd.getMood3()}));
            tmp.setTag(tag);

            songBarList.add(tmp);
            cnt++;
        }
        return songBarList;
    }
}


