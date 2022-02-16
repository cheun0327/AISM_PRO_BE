package com.upvote.aismpro.dto;

import com.upvote.aismpro.entity.Playlist;
import com.upvote.aismpro.entity.Song;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PlaylistDetailDTO {
    private Long playlistId;
    private String playlistCreatorId;
    private String playlistCreatorName;
    private String playlistName;
    private String playlistState;
    private List<String> playlistImgs;
    private List<SongDTO> songs;
    private List<String> keywords;
    private Integer playlistSongCount;
    private Integer playlistPlaytime;

    public PlaylistDetailDTO(Playlist playlist) {
        this.playlistId = playlist.getPlaylistId();
        this.playlistCreatorId = String.valueOf(playlist.getUser().getUserId());
        this.playlistCreatorName = playlist.getUser().getNickname();
        this.playlistName = playlist.getName();
        this.playlistState = playlist.getState().toString();
        this.songs = playlist.getSongs().stream().map(SongDTO::new).collect(Collectors.toList());
        this.keywords = new ArrayList<>();
        this.playlistSongCount = playlist.getSongs().size();
        this.playlistPlaytime = playlist.getSongs().stream()
                .mapToInt(song -> Integer.parseInt(song.getPlaytime()))
                .sum() / 60;
        this.playlistImgs = getImagesBySongList(playlist.getSongs());
    }

    private List<String> getImagesBySongList(List<Song> songList) {
        List<String> songImages = songList.stream()
                .map(Song::getImgFile)
                .collect(Collectors.toList());

        if (songImages.isEmpty()) {
            return Collections.singletonList("/playlistImg/defaultPlaylist.png");
        }

        if (songImages.size() < 4) {
            return Collections.singletonList("/songImg" + songImages.get(0));
        }

        return songImages.stream()
                .map(imgFile -> "/songImg/" + imgFile)
                .collect(Collectors.toList())
                .subList(0, 4);
    }
}

