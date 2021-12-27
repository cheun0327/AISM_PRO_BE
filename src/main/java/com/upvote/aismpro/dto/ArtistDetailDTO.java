package com.upvote.aismpro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistDetailDTO {
    private Long artistId;
    private String artistName;
    private String artistProfile;
    private List<String> tags;
}