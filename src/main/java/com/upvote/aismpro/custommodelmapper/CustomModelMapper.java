package com.upvote.aismpro.custommodelmapper;

import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.entity.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class CustomModelMapper {

    private final ModelMapper modelMapper = new ModelMapper();

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

    Converter<Song, List<String>> songTagCvt = context -> {
        KeywordPath keywordPath = context.getSource().getKeywordPath();

        String[] hashTags = keywordPath.getSubCategory().getHashTag().split("/");
        String lastTag = hashTags[hashTags.length - 1];

        if (lastTag.contains(",")) {
            hashTags = Arrays.copyOfRange(hashTags, 0, hashTags.length - 1);
        }

        List<String> filteredTags = Arrays.stream(hashTags)
                .collect(Collectors.toList());

        List<String> tags = Arrays.asList(
                keywordPath.getGenre(),
                keywordPath.getCategory(),
                keywordPath.getKeyword(),
                keywordPath.getFx().getKeyword()
        );

        Set<String> tagSet = new HashSet<>();
        tagSet.addAll(tags);
        tagSet.addAll(filteredTags);

        return tagSet.stream()
                .filter(tag -> !tag.isEmpty())
                .map(tag -> "#" + tag)
                .collect(Collectors.toList());
    };

    Converter<Song, String> songImgPathCvt = new Converter<Song, String>() {
        @Override
        public String convert(MappingContext<Song, String> context) {
            // ????????? ?????? : song/img/songId
            return context.getSource().getSongId() + ".png";
        }
    };

    Converter<Song, String> songFilePathCvt = new Converter<Song, String>() {
        @Override
        public String convert(MappingContext<Song, String> context) {
            // ?????? ?????? ?????? : song/wav/songId
            return context.getSource().getSongId() + ".mp3";
        }
    };

    Converter<Song, String> songMidiFilePathCvt = new Converter<Song, String>() {
        @Override
        public String convert(MappingContext<Song, String> context) {
            return context.getSource().getSongId() + ".mid";
        }
    };

    Converter<Song, Boolean> songLikeCvt = new Converter<Song, Boolean>() {
        @Override
        public Boolean convert(MappingContext<Song, Boolean> context) {
            // like default setting -> false???!
            return false;
        }
    };

    Converter<Song, String> songPlaytimeCvt = new Converter<Song, String>() {
        @Override
        public String convert(MappingContext<Song, String> context) {
            int playtime = Integer.parseInt(context.getSource().getPlaytime());
            String minute = String.valueOf(playtime / 60);
            String second = String.valueOf(playtime % 60);
            if (minute.length() == 1) minute = "0" + minute;
            if (second.length() == 1) second = "0" + second;
            return minute + ":" + second;
        }
    };

    @Bean
    public ModelMapper toSongDTO() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);

        Converter<LocalDateTime, Timestamp> songCreateDateCvt =
                ctx -> Timestamp.valueOf(ctx.getSource());

        modelMapper.createTypeMap(Song.class, SongDTO.class)
                .addMappings(modelMapper -> modelMapper.using(songTagCvt).map(src -> src, SongDTO::setTags))
                .addMappings(modelMapper -> modelMapper.using(songFilePathCvt).map(src -> src, SongDTO::setWavFile))
                .addMappings(modelMapper -> modelMapper.using(songMidiFilePathCvt).map(src -> src, SongDTO::setMidiFile))
                .addMappings(modelMapper -> modelMapper.using(songLikeCvt).map(src -> src, SongDTO::setLike))
                .addMappings(modelMapper -> modelMapper.using(songPlaytimeCvt).map(src -> src, SongDTO::setPlaytime))
                .addMappings(modelMapper -> modelMapper.using(songCreateDateCvt).map(Song::getCreateDate, SongDTO::setCreateDate))
                .addMapping(src -> src.getUser().getNickname(), SongDTO::setCreatorName)
                .addMapping(src -> src.getUser().getUserId(), SongDTO::setCreatorId)
                .addMapping(Song::getSongId, SongDTO::setSongId)
                .addMapping(Song::getSongName, SongDTO::setSongName)
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
    public ModelMapper songSaveDTO2song() {
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
            List<Song> songs = context.getSource().getSongs();
            Integer playtime = 0;
            for (Song s : songs) {
                playtime += Integer.parseInt(s.getPlaytime());
            }
            System.out.println(playtime / 60);
            return playtime / 60;
        }
    };

    Converter<Playlist, List<String>> playlistImgsCvt = new Converter<Playlist, List<String>>() {
        @Override
        public List<String> convert(MappingContext<Playlist, List<String>> context) {
            List<String> playlistImgs = new ArrayList<>();
            System.out.println("?????????????????? ?????????");
            System.out.println(context.getSource().getImgFile());
            if (context.getSource().getImgFile() == null) {
                // ?????? ??????
                if (context.getSource().getSongs().isEmpty()) {
                    playlistImgs.add("/playlistImg/defaultPlaylist.png");
                }
                // ?????? 4??? ??????
                else {
                    if (context.getSource().getSongs().size() < 4) {
                        // ?????? ?????? ?????????. ???????????? ???????????? ????????? ?????? ?????? ??? ?????????
                        List<String> songs = context.getSource().getSongs().stream().map(s -> s.getImgFile()).collect(Collectors.toList());
                        playlistImgs.add("/songImg/" + songs.get(0));
                    }
                    // ?????? 4??? ??????
                    else {
                        List<String> songs = context.getSource().getSongs().stream().map(s -> s.getImgFile()).collect(Collectors.toList());
                        playlistImgs = songs.subList(0, 4).stream().map(img -> "/songImg/" + img).collect(Collectors.toList());
                    }
                }
            } else {
                playlistImgs.add("/playlistImg/" + context.getSource().getImgFile());
            }
            System.out.println(playlistImgs);
            return playlistImgs;
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
                .addMappings(modelMapper -> modelMapper.using(playlistImgsCvt).map(src -> src, PlaylistDTO::setPlaylistImgs))
                .addMapping(Playlist::getName, PlaylistDTO::setPlaylistName)
                .addMapping(Playlist::getState, PlaylistDTO::setPlaylistState);
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
        System.out.println("?????????????????? ?????????");
        modelMapper.createTypeMap(Playlist.class, PlaylistDetailDTO.class)
                .addMappings(modelMapper -> modelMapper.using(playlistTagCvt).map(src -> src, PlaylistDetailDTO::setKeywords))
                .addMappings(modelMapper -> modelMapper.using(playlistImgsCvt).map(src -> src, PlaylistDetailDTO::setPlaylistImgs))
                .addMappings(modelMapper -> modelMapper.using(playlistSongCntCvt).map(src -> src, PlaylistDetailDTO::setPlaylistSongCount))
                .addMappings(modelMapper -> modelMapper.using(playlistPlaytimeCvt).map(src -> src, PlaylistDetailDTO::setPlaylistPlaytime))
                .addMapping(Playlist::getPlaylistId, PlaylistDetailDTO::setPlaylistId)
                .addMapping(Playlist::getName, PlaylistDetailDTO::setPlaylistName)
                .addMapping(Playlist::getState, PlaylistDetailDTO::setPlaylistState)
                .addMapping(src -> src.getUser().getUserId(), PlaylistDetailDTO::setPlaylistCreatorId)
                .addMapping(src -> src.getUser().getNickname(), PlaylistDetailDTO::setPlaylistCreatorName);

        return modelMapper;
    }

    @Bean
    public ModelMapper playlistSaveDTO2playlist() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.createTypeMap(PlaylistSaveDTO.class, Playlist.class)
                .addMapping(PlaylistSaveDTO::getPlaylistName, Playlist::setName)
                .addMapping(PlaylistSaveDTO::getState, Playlist::setState);
        return modelMapper;
    }

    @Bean
    public ModelMapper toArtistDTO() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.createTypeMap(User.class, ArtistDTO.class)
                .addMapping(User::getUserId, ArtistDTO::setArtistId)
                .addMapping(User::getNickname, ArtistDTO::setArtistName)
                .addMapping(User::getProfile, ArtistDTO::setArtistProfile);
        return modelMapper;
    }
}
