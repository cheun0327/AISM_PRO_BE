package com.upvote.aismpro.custommodelmapper;

import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.entity.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CustomModelMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    @Bean
    public ModelMapper standardMapper() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper;
    }

    @Bean
    public ModelMapper looseMapper() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }

    @Bean
    public ModelMapper playlistMapper() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.createTypeMap(PlayList.class, PlaylistDTO.class)
                .addMapping(PlayList::getPlaylistId, PlaylistDTO::setPlaylistId)
                .addMapping(PlayList::getName, PlaylistDTO::setName)
                .addMapping(PlayList::getState, PlaylistDTO::setState)
                .addMapping(PlayList::getImg, PlaylistDTO::setImg)
                .addMapping(src -> src.getUser().getId(), PlaylistDTO::setCreatorId);

        return modelMapper;
    }

    Converter<Create, List<String>> createTagCvt = new Converter<Create, List<String>>() {
        @Override
        public List<String> convert(MappingContext<Create, List<String>> context) {
            return new ArrayList<String>(Arrays.asList(
                    context.getSource().getSong().getFirstMood(),
                    context.getSource().getSong().getSecondMood(),
                    context.getSource().getSong().getThirdMood()
            ));
        }
    };

    @Bean
    public ModelMapper createMapper() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);

        modelMapper.createTypeMap(Create.class, CreateDTO.class)
                .addMappings(modelMapper -> modelMapper.using(createTagCvt).map(src -> src, CreateDTO::setTag))
                .addMapping(src -> src.getSong().getSongId(), CreateDTO::setSongId)
                .addMapping(src -> src.getSong().getSongName(), CreateDTO::setSongName)
                .addMapping(src -> src.getUser().getNickName(), CreateDTO::setCreatorName)
                .addMapping(src -> src.getSong().getThumbnail(), CreateDTO::setThumbnail)
                .addMapping(src -> src.getSong().getFileName(), CreateDTO::setFileName)
                .addMapping(src -> src.getSong().getCreateDate(), CreateDTO::setCreateDate)
                .addMapping(src -> src.getSong().getGenre(), CreateDTO::setGenre)
                .addMapping(src -> src.getSong().getLength(), CreateDTO::setLength);
        return modelMapper;
    }

    Converter<Buy, List<String>> buyTagCvt = new Converter<Buy, List<String>>() {
        @Override
        public List<String> convert(MappingContext<Buy, List<String>> context) {
            return new ArrayList<String>(Arrays.asList(
                    context.getSource().getSong().getFirstMood(),
                    context.getSource().getSong().getSecondMood(),
                    context.getSource().getSong().getThirdMood()
            ));
        }
    };

    @Bean
    public ModelMapper buyMapper() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);

        modelMapper.createTypeMap(Buy.class, BuyDTO.class)
                .addMappings(modelMapper -> modelMapper.using(buyTagCvt).map(src -> src, BuyDTO::setTag))
                .addMapping(src -> src.getSong().getSongId(), BuyDTO::setSongId)
                .addMapping(src -> src.getSong().getSongName(), BuyDTO::setSongName)
                .addMapping(src -> src.getUser().getNickName(), BuyDTO::setCreatorName)
                .addMapping(src -> src.getUser().getId(), BuyDTO::setOwnerId)
                .addMapping(src -> src.getUser().getNickName(), BuyDTO::setOwnerName)
                .addMapping(src -> src.getSong().getThumbnail(), BuyDTO::setThumbnail)
                .addMapping(src -> src.getSong().getFileName(), BuyDTO::setFileName)
                .addMapping(src -> src.getSong().getCreateDate(), BuyDTO::setCreateDate)
                .addMapping(src -> src.getSong().getGenre(), BuyDTO::setGenre)
                .addMapping(src -> src.getSong().getLength(), BuyDTO::setLength);
        return modelMapper;
    }

    Converter<Sell, List<String>> sellTagCvt = new Converter<Sell, List<String>>() {
        @Override
        public List<String> convert(MappingContext<Sell, List<String>> context) {
            return new ArrayList<String>(Arrays.asList(
                    context.getSource().getSong().getFirstMood(),
                    context.getSource().getSong().getSecondMood(),
                    context.getSource().getSong().getThirdMood()
            ));
        }
    };

    @Bean
    public ModelMapper sellMapper() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.createTypeMap(Sell.class, SellDTO.class)
                .addMappings(modelMapper -> modelMapper.using(sellTagCvt).map(src -> src, SellDTO::setTag))
                .addMapping(src -> src.getSong().getSongId(), SellDTO::setSongId)
                .addMapping(src -> src.getSong().getSongName(), SellDTO::setSongName)
                .addMapping(src -> src.getUser().getNickName(), SellDTO::setCreatorName)
                .addMapping(src -> src.getSong().getThumbnail(), SellDTO::setThumbnail)
                .addMapping(src -> src.getSong().getFileName(), SellDTO::setFileName)
                .addMapping(src -> src.getSong().getCreateDate(), SellDTO::setCreateDate)
                .addMapping(src -> src.getSong().getGenre(), SellDTO::setGenre)
                .addMapping(src -> src.getSong().getLength(), SellDTO::setLength);
        return modelMapper;
    }

    Converter<Like, List<String>> likeTagCvt = new Converter<Like, List<String>>() {
        @Override
        public List<String> convert(MappingContext<Like, List<String>> context) {
            return new ArrayList<String>(Arrays.asList(
                    context.getSource().getSong().getFirstMood(),
                    context.getSource().getSong().getSecondMood(),
                    context.getSource().getSong().getThirdMood()
            ));
        }
    };

    @Bean
    public ModelMapper likeMapper() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.createTypeMap(Like.class, LikeDTO.class)
                .addMappings(modelMapper -> modelMapper.using(likeTagCvt).map(src -> src, LikeDTO::setTag))
                .addMapping(src -> src.getSong().getSongId(), LikeDTO::setSongId)
                .addMapping(src -> src.getSong().getSongName(), LikeDTO::setSongName)
                .addMapping(src -> src.getUser().getNickName(), LikeDTO::setCreatorName)
                .addMapping(src -> src.getUser().getId(), LikeDTO::setOwnerId)
                .addMapping(src -> src.getUser().getNickName(), LikeDTO::setOwnerName)
                .addMapping(src -> src.getSong().getThumbnail(), LikeDTO::setThumbnail)
                .addMapping(src -> src.getSong().getFileName(), LikeDTO::setFileName)
                .addMapping(src -> src.getSong().getCreateDate(), LikeDTO::setCreateDate)
                .addMapping(src -> src.getSong().getGenre(), LikeDTO::setGenre)
                .addMapping(src -> src.getSong().getLength(), LikeDTO::setLength);
        return modelMapper;
    }

}
