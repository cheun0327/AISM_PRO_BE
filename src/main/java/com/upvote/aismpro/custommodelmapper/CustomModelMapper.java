package com.upvote.aismpro.custommodelmapper;

import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.entity.PlayList;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
