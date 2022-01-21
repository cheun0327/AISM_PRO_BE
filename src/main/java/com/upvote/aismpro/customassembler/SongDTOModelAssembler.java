package com.upvote.aismpro.customassembler;

import com.upvote.aismpro.controller.SongController;
import com.upvote.aismpro.custommodel.SongDTOModel;
import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.SongDTO;
import com.upvote.aismpro.entity.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SongDTOModelAssembler extends RepresentationModelAssemblerSupport<SongDTO, SongDTOModel>{

    public SongDTOModelAssembler() {
        super(SongController.class, SongDTOModel.class);
    }

    @Override
    public SongDTOModel toModel(SongDTO songDTO) {
        SongDTOModel songDTOModel = new SongDTOModel(songDTO);

        songDTOModel.add(
                linkTo(methodOn(SongController.class).getSongDetail(songDTO.getSongId())
                ).withSelfRel());

        return songDTOModel;
    }

    @Override
    public CollectionModel<SongDTOModel> toCollectionModel(Iterable<? extends SongDTO> songDTOs) {
        CollectionModel<SongDTOModel> songDTOModels = super.toCollectionModel(songDTOs);

//        songDTOModels.add(linkTo(methodOn(SongController.class).getSongDetail(2L)).withSelfRel());

        return songDTOModels;
    }

    public List<SongDTOModel> toModelList(List<SongDTO> songDTOs) {
        if (songDTOs.isEmpty()) return Collections.emptyList();

        return songDTOs.stream()
                .map(s -> toModel(s))
                .collect(Collectors.toList());

    }
}
