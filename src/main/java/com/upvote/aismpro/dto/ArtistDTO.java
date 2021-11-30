package com.upvote.aismpro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArtistDTO {
    private Long artistId;
    private String artistName;
    private String artistProfile;

    public ArtistDTO(Long artistId, String artistName, String artistProfile) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistProfile = artistProfile;
    }
}
