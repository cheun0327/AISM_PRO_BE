package com.upvote.aismpro.custommodelmapper;

import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.entity.GenreInfo;
import com.upvote.aismpro.entity.Playlist;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.SongRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CustomModelMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private SongRepository songRepository;

    @Bean
    public ModelMapper toUserDTO() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);

        modelMapper.createTypeMap(User.class, UserDTO.class)
                .addMapping(User::getUserId, UserDTO::setUserId)
                .addMapping(User::getEmail, UserDTO::setEmail)
                .addMapping(User::getNickname, UserDTO::setNickname)
                .addMapping(User::getPlatform, UserDTO::setPlatform)
                .addMapping(User::getProfile, UserDTO::setProfile);

        return modelMapper;
    }

    Converter<GenreInfo, Integer> genreInfoCntCvt = new Converter<GenreInfo, Integer>() {
        @Override
        public Integer convert(MappingContext<GenreInfo, Integer> context) {
            String categories[] = {context.getSource().getOne(), context.getSource().getTwo(),
                context.getSource().getThree(), context.getSource().getFour(), context.getSource().getFive(), context.getSource().getSix()};
            Integer cnt = 0;
            for (String cate : categories) if (cate != null) cnt++;
            return cnt;
        }
    };

    @Bean
    public ModelMapper toGenreInfoDTO() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);

        modelMapper.createTypeMap(GenreInfo.class, GenreInfoDTO.class)
                .addMappings(modelMapper -> modelMapper.using(genreInfoCntCvt).map(src -> src, GenreInfoDTO::setCategoryCnt));
        return modelMapper;
    }

    Converter<Song, List<String>> songTagCvt = new Converter<Song, List<String>>() {
        @Override
        public List<String> convert(MappingContext<Song, List<String>> context) {
            String categories[] = {
                    context.getSource().getOne(), context.getSource().getTwo(), context.getSource().getThree(),
                    context.getSource().getFour(), context.getSource().getFive(), context.getSource().getSix()
            };
            List<String> tags = new ArrayList<>();
            Arrays.stream(categories).forEach(s -> {if (s != null)  tags.add(s);});
            return tags;
        }
    };

    Converter<Song, String> songImgPathCvt = new Converter<Song, String>() {
        @Override
        public String convert(MappingContext<Song, String> context) {
            // 이미지 경로 : song/img/songId
            return context.getSource().getSongId() + ".png";
        }
    };

    Converter<Song, String> songFilePathCvt = new Converter<Song, String>() {
        @Override
        public String convert(MappingContext<Song, String> context) {
            // 음원 파일 경로 : song/wav/songId
            return context.getSource().getSongId() + ".wav";
        }
    };

    Converter<Song, Boolean> songLikeCvt = new Converter<Song, Boolean>() {
        @Override
        public Boolean convert(MappingContext<Song, Boolean> context) {
            // like default setting -> false로!
            return false;
        }
    };

    @Bean
    public ModelMapper toSongDTO() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);

        modelMapper.createTypeMap(Song.class, SongDTO.class)
                .addMappings(modelMapper -> modelMapper.using(songTagCvt).map(src -> src, SongDTO::setTags))
                .addMappings(modelMapper -> modelMapper.using(songFilePathCvt).map(src -> src, SongDTO::setWavFile))
                .addMappings(modelMapper -> modelMapper.using(songLikeCvt).map(src -> src, SongDTO::setLike))
                .addMapping(src -> src.getUser().getNickname(), SongDTO::setCreatorName)
                .addMapping(src -> src.getUser().getUserId(), SongDTO::setCreatorId)
                .addMapping(Song::getSongId, SongDTO::setSongId)
                .addMapping(Song::getSongName, SongDTO::setSongName)
                .addMapping(Song::getCreateDate, SongDTO::setCreateDate)
                .addMapping(Song::getPlaytime, SongDTO::setPlaytime)
                .addMapping(Song::getImgFile, SongDTO::setImgFile);
        return modelMapper;
    }

    @Bean
    public ModelMapper toShortSongDTO() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);

        modelMapper.createTypeMap(Song.class, ShortSongDTO.class)
                .addMappings(modelMapper -> modelMapper.using(songTagCvt).map(src -> src, ShortSongDTO::setTags))
                .addMappings(modelMapper -> modelMapper.using(songLikeCvt).map(src -> src, ShortSongDTO::setLike))
                .addMapping(src -> src.getUser().getNickname(), ShortSongDTO::setCreatorName)
                .addMapping(src -> src.getUser().getUserId(), ShortSongDTO::setCreatorId)
                .addMapping(Song::getSongId, ShortSongDTO::setSongId)
                .addMapping(Song::getSongName, ShortSongDTO::setSongName)
                .addMapping(Song::getCreateDate, ShortSongDTO::setCreateDate);
        return modelMapper;
    }

    @Bean
    public ModelMapper songSaveDTO2song(){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);

        modelMapper.createTypeMap(SongSaveDTO.class, Song.class);

        return modelMapper;
    }


    Converter<Playlist, Integer> playlistSongCntCvt = new Converter<Playlist, Integer>() {
        @Override
        public Integer convert(MappingContext<Playlist, Integer> context) {
            return context.getSource().getSongs().size();
        }
    };

    Converter<Playlist, Integer> playlistPlaytimeCvt = new Converter<Playlist, Integer>() {
        @Override
        public Integer convert(MappingContext<Playlist, Integer> context) {
            AudioFileFormat aff = null;
            try {
                aff = AudioSystem.getAudioFileFormat(new File("./song/sos.wav"));
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AudioFormat af = aff.getFormat();
            double playTime = (double) aff.getFrameLength() / af.getFrameRate() * 3.7;
            return (int) Math.ceil((context.getSource().getSongs().size() * (int) Math.ceil(playTime)) / 60);
        }
    };

    @Bean
    public ModelMapper toPlaylistDTO() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);

        modelMapper.createTypeMap(Playlist.class, PlaylistDTO.class)
                .addMapping(src -> src.getUser().getNickname(), PlaylistDTO::setPlaylistCreatorName)
                .addMappings(modelMapper -> modelMapper.using(playlistSongCntCvt).map(src -> src, PlaylistDTO::setPlaylistSongCount))
                .addMappings(modelMapper -> modelMapper.using(playlistPlaytimeCvt).map(src -> src, PlaylistDTO::setPlaylistPlaytime))
                .addMapping(Playlist::getName, PlaylistDTO::setPlaylistName)
                .addMapping(Playlist::getState, PlaylistDTO::setPlaylistState)
                .addMapping(Playlist::getImg, PlaylistDTO::setPlaylistImg);
        return modelMapper;
    }

    Converter<Playlist, List<String>> playlistTagCvt = new Converter<Playlist, List<String>>() {
        @Override
        public List<String> convert(MappingContext<Playlist, List<String>> context) {
            return new ArrayList<String>(Arrays.asList(
                    context.getSource().getOne(),
                    context.getSource().getTwo(),
                    context.getSource().getThree()
            ));
        }
    };

    @Bean
    public ModelMapper toPlaylistDetailDTO() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.createTypeMap(Playlist.class, PlaylistDetailDTO.class)
                .addMappings(modelMapper -> modelMapper.using(playlistTagCvt).map(src -> src, PlaylistDetailDTO::setKeywords))
                .addMapping(Playlist::getPlaylistId, PlaylistDetailDTO::setPlaylistId)
                .addMapping(Playlist::getName, PlaylistDetailDTO::setPlaylistName)
                .addMapping(Playlist::getState, PlaylistDetailDTO::setPlaylistState)
                .addMapping(Playlist::getImg, PlaylistDetailDTO::setPlaylistImg)
                .addMapping(src -> src.getUser().getUserId(), PlaylistDetailDTO::setPlaylistCreatorId)
                .addMapping(src -> src.getUser().getNickname(), PlaylistDetailDTO::setPlaylistCreatorName);

        return modelMapper;
    }

}
