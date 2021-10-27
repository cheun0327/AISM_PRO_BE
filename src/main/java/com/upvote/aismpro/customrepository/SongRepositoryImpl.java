package com.upvote.aismpro.customrepository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.dto.MoodDTO;
import com.upvote.aismpro.entity.QSong;
import com.upvote.aismpro.entity.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SongRepositoryImpl implements SongRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private QSong song = QSong.song;

    @Override
    public List<Song> findSongByIdListQD(List<String> songIdList){
        return queryFactory.select(song)
                .from(song)
                .where(songIdIn(songIdList))
                .fetch();
    }

    @Override
    public List<Song> findSongBySearchParamQD(LibrarySearchDTO librarySearchDTO) {
        return queryFactory.select(song)
                .from(song)
                .where(
                        typeIn(librarySearchDTO.getType())
                )
                .where(
                        orQueryWrapper(librarySearchDTO)
                )
                .fetch();
    }

    @Override
    public List<Song> findSimilarSongQD(MoodDTO moodDTO) {
        return queryFactory.select(song)
                .from(song)
                .where(
                        song.genre.eq(moodDTO.getGenre())
                                .and(song.secondMood.eq(moodDTO.getSecondMood()))
                )
                .fetch();
    }

    private BooleanExpression songIdIn(List<String> songIdList) {
        return songIdList.isEmpty() ? null : song.songId.in(songIdList);
    }

    private BooleanExpression typeIn(List<String> type) {
        return type.isEmpty() ? null : song.type.in(type);
    }

    private BooleanExpression orQueryWrapper(LibrarySearchDTO librarySearchDTO) {
        if (librarySearchDTO.getLength().isEmpty() && librarySearchDTO.getGenre().isEmpty()
            && librarySearchDTO.getFirstMood().isEmpty() && librarySearchDTO.getFirstMood().isEmpty()) {
            return null;
        }
        else {
            return song.length.in(librarySearchDTO.getLength())
                    .or(genreIn(librarySearchDTO.getGenre()))
                    .or(firstMoodIn(librarySearchDTO.getFirstMood()))
                    .or(secondMoodIn(librarySearchDTO.getSecondMood()));
        }
    }

    private BooleanExpression genreIn(List<String> genre) {
        return genre.isEmpty() ? null : song.genre.in(genre);
    }

    private BooleanExpression firstMoodIn (List<String> firstMood) {
        return firstMood.isEmpty() ? null : song.firstMood.in(firstMood);
    }

    private BooleanExpression secondMoodIn (List<String> secondMood) {
        return secondMood.isEmpty() ? null : song.secondMood.in(secondMood);
    }
}
