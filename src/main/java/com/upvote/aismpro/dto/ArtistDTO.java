package com.upvote.aismpro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistDTO {
    private Long artistId;
    private String artistName;
    private String artistProfile;

}
