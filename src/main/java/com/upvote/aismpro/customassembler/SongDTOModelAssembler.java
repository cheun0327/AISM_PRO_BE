package com.upvote.aismpro.customassembler;

import com.upvote.aismpro.controller.SongController;
import com.upvote.aismpro.custommodel.SongDTOModel;
import com.upvote.aismpro.dto.SongDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SongDTOModelAssembler extends RepresentationModelAssemblerSupport<SongDTO, SongDTOModel>{

    public SongDTOModelAssembler() {
        super(SongController.class, SongDTOModel.class);
    }

    @Override
    public SongDTOModel toModel(SongDTO songDTO) {
        SongDTOModel songDTOModel = instantiateModel(songDTO);

        songDTOModel.add(
                linkTo(methodOn(SongController.class).getSongDetail(songDTO.getSongId())
                ).withSelfRel());

        return songDTOModel;
    }

    @Override
    public CollectionModel<SongDTOModel> dtoModelCollectionModel(Iterable<? extends SongDTO> songDTOs) {
        CollectionModel<SongDTOModel> songDTOModels = super.toCollectionModel(songDTOs);

        return songDTOModels;
    }
}
