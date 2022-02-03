package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.GenreInfoDTO;
import com.upvote.aismpro.dto.KeywordListResDTO;
import com.upvote.aismpro.entity.Genre;
import com.upvote.aismpro.entity.GenreOptionOrder;
import com.upvote.aismpro.entity.OptionKeywordNode;
import com.upvote.aismpro.repository.GenreOptionOrderRepository;
import com.upvote.aismpro.repository.GenreRepository;
import com.upvote.aismpro.repository.KeywordRepository;
import com.upvote.aismpro.repository.OptionKeywordNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ComposeService implements ComposeServiceInter {

    private final GenreRepository genreRepository;
    private final GenreOptionOrderRepository genreOptionOrderRepository;
    private final KeywordRepository keywordRepository;
    private final OptionKeywordNodeRepository optionKeywordNodeRepository;

    public List<String> getGenreList() {
        return genreRepository.findAll(Sort.by(Sort.DEFAULT_DIRECTION, "genreOrder"))
                .stream().map(Genre::getGenreName)
                .collect(Collectors.toList());
    }

    public GenreInfoDTO getCategoryList(String genre) {
        List<GenreOptionOrder> categoryList = genreOptionOrderRepository.findAllByGenreName(genre);

        GenreInfoDTO responseDTO = new GenreInfoDTO();

        Map<Integer, Consumer<String>> setterMapper = new HashMap<>();
        setterMapper.put(1, responseDTO::setTwo);
        setterMapper.put(2, responseDTO::setThree);
        setterMapper.put(3, responseDTO::setFour);
        setterMapper.put(4, responseDTO::setFive);
        setterMapper.put(5, responseDTO::setSix);

        responseDTO.setOne("장르");
        responseDTO.setGenre(genre);
        responseDTO.setCategoryCnt(categoryList.size() + 1);
        categoryList.forEach(option ->
                setterMapper.get(option.getOrder()).accept(option.getGenreOption().getOptionName()));

        return responseDTO;
    }

    public List<String> get2ndList(String one) {
        return optionKeywordNodeRepository.findRootListByGenreName(one).stream()
                .map(node -> node.getKeyword().getKeywordName())
                .collect(Collectors.toList());
    }

    public KeywordListResDTO findChildKeyword(List<Long> nodeIdList) {
        String path = nodeIdList.stream()
                .map(Object::toString)
                .collect(Collectors.joining("/"));

        int depth = nodeIdList.size();

        List<OptionKeywordNode> keywordList = optionKeywordNodeRepository.findChildByPathAndDepth(path, depth);

        return new KeywordListResDTO(keywordList);
    }

    public List<String> get3rdList(String one, String two) throws Exception {
        List<String> threes = keywordRepository.find3rdQD(one, two);
        if (threes.isEmpty()) throw new Exception();
        return threes;
    }

    public List<String> get4thList(String one, String two, String three) throws Exception {
        List<String> fours = keywordRepository.find4thQD(one, two, three);
        if (fours.isEmpty()) throw new Exception();
        return fours;
    }

    public List<String> get5thList(String one, String two, String three, String four) throws Exception {
        List<String> fives = keywordRepository.find5thQD(one, two, three, four);
        if (fives.isEmpty()) throw new Exception();
        return fives;
    }

    public List<String> get6thList(String one, String two, String three, String four, String five) throws Exception {
        List<String> sixs = keywordRepository.find6thQD(one, two, three, four, five);
        if (sixs.isEmpty()) throw new Exception();
        return sixs;
    }

}
