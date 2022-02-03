package com.upvote.aismpro.dto;

import com.upvote.aismpro.entity.OptionKeywordNode;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class KeywordListResDTO {

    private final List<KeywordDTO> keywordList;

    public KeywordListResDTO(List<OptionKeywordNode> entityList) {
        this.keywordList = entityList.stream()
                .map(KeywordDTO::new)
                .collect(Collectors.toList());
    }

    @Getter
    static class KeywordDTO {

        private final Long id;
        private final String name;

        public KeywordDTO(OptionKeywordNode entity) {
            this.id = entity.getNodeId();
            this.name = entity.getKeyword().getKeywordName();
        }
    }
}
