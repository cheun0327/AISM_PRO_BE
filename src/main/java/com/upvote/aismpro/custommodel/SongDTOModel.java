package com.upvote.aismpro.custommodel;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.upvote.aismpro.dto.SongDTO;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;
import java.util.List;

@Data
public class SongDTOModel extends RepresentationModel<SongDTOModel> {

    public SongDTOModel (SongDTO songDTO) {
        this.songDTO = songDTO;
    }

    // depth를 없애고 songDTO의 멤버변수를 바로 출력 받을 수 있게하는 어노테이션
    @JsonUnwrapped
    private final SongDTO songDTO;

}
