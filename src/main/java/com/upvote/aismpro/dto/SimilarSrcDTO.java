package com.upvote.aismpro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SimilarSrcDTO {
    private Long songId;

    private Long creatorId;

    private List<String> tags;
}
