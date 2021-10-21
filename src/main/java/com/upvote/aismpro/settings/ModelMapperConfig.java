package com.upvote.aismpro.settings;

import com.upvote.aismpro.dto.*;
import com.upvote.aismpro.entity.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

import javax.management.modelmbean.ModelMBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sun.tools.internal.xjc.reader.Ring.add;

@Configuration
public class ModelMapperConfig {

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

    @Bean
    public ModelMapper createMapper() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD);

        modelMapper.createTypeMap(Create.class, CreateDTO.class)
                .addMapping(src -> src.getSong().getSongId(), CreateDTO::setSongId)
                .addMapping(src -> src.getSong().getSongName(), CreateDTO::setSongName)
                .addMapping(src -> src.getUser().getNickName(), CreateDTO::setCreatorName)
                .addMapping(src -> new ArrayList<String>(
                        Arrays.asList(src.getSong().getFirstMood(),
                                src.getSong().getSecondMood(), src.getSong().getThirdMood())
                ), CreateDTO::setTag)
                .addMapping(src -> src.getSong().getThumbnail(), CreateDTO::setThumbnail)
                .addMapping(src -> src.getSong().getFileName(), CreateDTO::setFileName)
                .addMapping(src -> src.getSong().getCreateDate(), CreateDTO::setCreateDate)
                .addMapping(src -> src.getSong().getGenre(), CreateDTO::setGenre)
                .addMapping(src -> src.getSong().getLength(), CreateDTO::setLength);
        return modelMapper;
    }

    @Bean
    public ModelMapper buyMapper() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD);

        modelMapper.createTypeMap(Buy.class, BuyDTO.class)
                .addMapping(src -> src.getSong().getSongId(), BuyDTO::setSongId)
                .addMapping(src -> src.getSong().getSongName(), BuyDTO::setSongName)
                .addMapping(src -> src.getUser().getNickName(), BuyDTO::setCreatorName)
                .addMapping(src -> src.getUser().getId(), BuyDTO::setOwnerId)
                .addMapping(src -> src.getUser().getNickName(), BuyDTO::setOwnerName)
                .addMapping(src -> new ArrayList<String>(
                        Arrays.asList(src.getSong().getFirstMood(),
                                src.getSong().getSecondMood(), src.getSong().getThirdMood())
                ), BuyDTO::setTag)
                .addMapping(src -> src.getSong().getThumbnail(), BuyDTO::setThumbnail)
                .addMapping(src -> src.getSong().getFileName(), BuyDTO::setFileName)
                .addMapping(src -> src.getSong().getCreateDate(), BuyDTO::setCreateDate)
                .addMapping(src -> src.getSong().getGenre(), BuyDTO::setGenre)
                .addMapping(src -> src.getSong().getLength(), BuyDTO::setLength);
        return modelMapper;
    }

    @Bean
    public ModelMapper sellMapper() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD);

        modelMapper.createTypeMap(Sell.class, SellDTO.class)
                .addMapping(src -> src.getSong().getSongId(), SellDTO::setSongId)
                .addMapping(src -> src.getSong().getSongName(), SellDTO::setSongName)
                .addMapping(src -> src.getUser().getNickName(), SellDTO::setCreatorName)
                .addMapping(src -> new ArrayList<String>(
                        Arrays.asList(src.getSong().getFirstMood(),
                                src.getSong().getSecondMood(), src.getSong().getThirdMood())
                ), SellDTO::setTag)
                .addMapping(src -> src.getSong().getThumbnail(), SellDTO::setThumbnail)
                .addMapping(src -> src.getSong().getFileName(), SellDTO::setFileName)
                .addMapping(src -> src.getSong().getCreateDate(), SellDTO::setCreateDate)
                .addMapping(src -> src.getSong().getGenre(), SellDTO::setGenre)
                .addMapping(src -> src.getSong().getLength(), SellDTO::setLength);
        return modelMapper;
    }

    @Bean
    public ModelMapper likeMapper() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD);

        modelMapper.createTypeMap(Like.class, LikeDTO.class)
                .addMapping(src -> src.getSong().getSongId(), LikeDTO::setSongId)
                .addMapping(src -> src.getSong().getSongName(), LikeDTO::setSongName)
                .addMapping(src -> src.getUser().getNickName(), LikeDTO::setCreatorName)
                .addMapping(src -> src.getUser().getId(), LikeDTO::setOwnerId)
                .addMapping(src -> src.getUser().getNickName(), LikeDTO::setOwnerName)
                .addMapping(src -> new ArrayList<String>(
                        Arrays.asList(src.getSong().getFirstMood(),
                                src.getSong().getSecondMood(), src.getSong().getThirdMood())
                ), LikeDTO::setTag)
                .addMapping(src -> src.getSong().getThumbnail(), LikeDTO::setThumbnail)
                .addMapping(src -> src.getSong().getFileName(), LikeDTO::setFileName)
                .addMapping(src -> src.getSong().getCreateDate(), LikeDTO::setCreateDate)
                .addMapping(src -> src.getSong().getGenre(), LikeDTO::setGenre)
                .addMapping(src -> src.getSong().getLength(), LikeDTO::setLength);
        return modelMapper;
    }

}
