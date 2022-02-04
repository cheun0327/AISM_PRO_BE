package com.upvote.aismpro.service;

import com.google.api.client.util.Lists;
import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.MyLibrarySearchDTO;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.dto.SongSaveDTO;
import com.upvote.aismpro.dto.SongTagDTO;
import com.upvote.aismpro.entity.Like;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.exception.ApiException;
import com.upvote.aismpro.repository.LikeRepository;
import com.upvote.aismpro.repository.SongRepository;
import com.upvote.aismpro.repository.UserRepository;
import com.upvote.aismpro.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class SongService implements SongServiceInter {

    private final SongRepository songRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final CustomModelMapper modelMapper;

    // 생성 song 저장
    public SongDTO saveSong(SongSaveDTO songSave, MultipartFile file) throws Exception, FileUploadException {
        Long userId = SecurityUtil.getCurrentUserId();

        try {
            Song song = modelMapper.songSaveDTO2song().map(songSave, Song.class);
            song.setUser(userRepository.getById(userId));

            // song 정보 저장
            Song savedSong = songRepository.save(song);
            if (savedSong.getSongName().equals("음원 제목")) savedSong.setSongName("Song" + savedSong.getSongId());

            // song img 저장
            if (file != null) {
                String dirPath = "/var/lib/jenkins/workspace/img/song";
                String imgName = savedSong.getSongId() + "." + extractExt(file.getOriginalFilename());

                file.transferTo(new File(dirPath + "/" + imgName));
                savedSong.setImgFile(imgName);
            }
            savedSong.setPlaytime(String.valueOf(moveSongFiles(savedSong.getSongId())));
            songRepository.save(savedSong);

            // songDTO
            SongDTO songDTO = modelMapper.toSongDTO().map(savedSong, SongDTO.class);
            System.out.println(songDTO);

            return songDTO;

        } catch (Exception e) {
            System.out.println("일반 에러로!!!!!");
//            e.printStackTrace();
            throw new Exception();
        }
    }

    // song 삭제
    public void deleteSong(Long songId) {
        songRepository.deleteById(songId);
    }

    public int moveSongFiles(Long songId) throws IOException, CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException {
        Long userId = SecurityUtil.getCurrentUserId();
        String dirPath = "/var/lib/jenkins/workspace/song";
        String midiDirPath = "/var/lib/jenkins/workspace/midi";

        // 생성 곡 저장 위치 디렉토리 확인
        String songDirPath = dirPath + "/" + userId + "/tmp/" + userId + ".mp3";
        String songMidiPath = dirPath + "/" + userId + "/tmp/" + userId + "_ALL Track.mid";

        // 저장된 곡 위치 이동
        File source = new File(songDirPath);
        File target = new File(dirPath + "/" + songId + ".mp3");
        FileUtils.moveFile(source, target);

        // 저장된 미디 파일 위치 이동
        File midiSource = new File(songMidiPath);
        File midiTarget = new File(midiDirPath + "/" + songId + ".mid");
        FileUtils.moveFile(midiSource, midiTarget);

        // 음원 재생시간 계산
        java.util.logging.Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
        AudioFile audioFile = AudioFileIO.read(target);
        int playtime = audioFile.getAudioHeader().getTrackLength();

        return playtime;
    }

    // song detail 페이지에 뿌릴 상세 정보 리턴
    // like 어떻게 뿌려줄지 생각
    public SongDTO getSongDetail(Long songId) {
        Optional<Song> songOpt = songRepository.findBySongId(songId);
        Song song = songOpt.orElseThrow(() -> new NoSuchElementException());
        return modelMapper.toSongDTO().map(song, SongDTO.class);
    }

    // 비슷한 곡 가져오기
    public List<SongDTO> getSimilarSong(Long songId) {
        Song song = songRepository.getById(songId);
        List<SongDTO> similar = songRepository.findSimilarSongQD(song)
                .stream()
                .map(s -> modelMapper.toSongDTO().map(s, SongDTO.class))
                .collect(Collectors.toList());
        Collections.shuffle(similar);
        if (similar.size() > 6) return Lists.newArrayList(similar.subList(0, 6));
        return similar;
    }

    // 작곡하기 step2 비슷한 곡 가져오기
    public List<SongDTO> getSimilarSongByTags(SongTagDTO songTagDTO) {
        List<SongDTO> similar = songRepository.findSimilarSongByTagsQD(songTagDTO)
                .stream()
                .map(s -> modelMapper.toSongDTO().map(s, SongDTO.class))
                .collect(Collectors.toList());
        Collections.shuffle(similar);
        if (similar.size() > 6) return Lists.newArrayList(similar.subList(0, 6));
        return similar;
    }

    public Integer getLikeCnt(Long songId) {
        Integer cnt = likeRepository.countBySong_SongId(songId);
        return cnt;
    }

    public SongDTO setLike2SongDTO(SongDTO songDTO, Long userId) throws Exception {
        try {
            List<Like> likes = likeRepository.findAllByUser_UserIdAndSong_SongId(userId, songDTO.getSongId());

            if (likes.size() == 1) songDTO.setLike(true);

            return songDTO;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public List<SongDTO> setLike2SongDTOList(List<SongDTO> songDTOList, Long userId) {
        try {
            List<Long> likes = likeRepository.findAllByUser_UserId(userId)
                    .stream()
                    .map(s -> s.getSong().getSongId())
                    .collect(Collectors.toList());

            for (SongDTO s : songDTOList) {
                s.setLike(likes.contains(s.getSongId()));
            }

            return songDTOList;
        } catch (Exception e) {
            throw new ApiException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
            );
        }
    }

    // 업로드 파일 확장자 추출
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public List<SongDTO> getSongListByUserId(Long userId) {
        return songRepository.findAllByUserIdFetchUserQD(userId).stream()
                .map(song -> modelMapper.toSongDTO().map(song, SongDTO.class))
                .collect(Collectors.toList());
    }

    public List<SongDTO> searchSongList(MyLibrarySearchDTO reqDto) {
        Long userId = SecurityUtil.getCurrentUserId();
        String searchStr = reqDto.getSearch();

        return songRepository.searchSongListQD(userId, searchStr).stream()
                .map(song -> modelMapper.toSongDTO().map(song, SongDTO.class))
                .collect(Collectors.toList());
    }

    public void deleteSongs(List<Long> songIdList) {
        songRepository.deleteAllInSongIdList(LocalDateTime.now(), songIdList);
    }
}
