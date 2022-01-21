package com.upvote.aismpro.custommodel;


import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.upvote.aismpro.dto.SongDTO;
import org.springframework.hateoas.RepresentationModel;

public class SongDTOModel extends RepresentationModel<SongDTOModel> {

    public SongDTOModel(SongDTO songDTO) {
        this.songDTO = songDTO;
    }

    // depth를 없애고 songDTO의 멤버변수를 바로 출력 받을 수 있게하는 어노테이션
    @JsonUnwrapped
    private final SongDTO songDTO;
}
