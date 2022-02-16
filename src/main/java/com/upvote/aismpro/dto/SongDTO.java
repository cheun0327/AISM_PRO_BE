package com.upvote.aismpro.dto;

import com.upvote.aismpro.entity.KeywordPath;
import com.upvote.aismpro.entity.Song;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.EntityModel;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
public class SongDTO extends EntityModel<SongDTO> {
    private Long songId;
    private String songName;
    private Long creatorId;
    private String creatorName;
    private Timestamp createDate;
    private List<String> tags;
    private String playtime;
    private String wavFile;
    private String imgFile;
    private String midiFile;
    private Boolean like;

    public SongDTO(Song song) {
        this.songId = song.getSongId();
        this.songName = song.getSongName();
        this.creatorId = song.getUser().getUserId();
        this.creatorName = song.getUser().getNickname();
        this.createDate = Timestamp.valueOf(song.getCreateDate());
        this.tags = getTags(song);
        this.playtime = getPlaytime(song.getPlaytime());
        this.wavFile = song.getSongId() + ".mp3";
        this.imgFile = song.getImgFile();
        this.midiFile = song.getSongId() + ".mid";
    }

    private String getPlaytime(String playtime) {
        int playtimeInt = Integer.parseInt(playtime);
        String minute = String.valueOf(playtimeInt / 60);
        String second = String.valueOf(playtimeInt % 60);
        if (minute.length() == 1) minute = "0" + minute;
        if (second.length() == 1) second = "0" + second;
        return minute + ":" + second;
    }

    private List<String> getTags(Song song) {
        KeywordPath keywordPath = song.getKeywordPath();

        String[] hashTags = keywordPath.getSubCategory().getHashTag().split("/");
        String lastTag = hashTags[hashTags.length - 1];

        if (lastTag.contains(",")) {
            hashTags = Arrays.copyOfRange(hashTags, 0, hashTags.length - 1);
        }

        List<String> filteredTags = Arrays.stream(hashTags)
                .collect(Collectors.toList());

        List<String> tags = Arrays.asList(
                keywordPath.getGenre(),
                keywordPath.getCategory(),
                keywordPath.getKeyword(),
                keywordPath.getFx().getKeyword()
        );

        Set<String> tagSet = new HashSet<>();
        tagSet.addAll(tags);
        tagSet.addAll(filteredTags);

        return tagSet.stream()
                .filter(tag -> !tag.isEmpty())
                .map(tag -> "#" + tag)
                .collect(Collectors.toList());
    }
}

