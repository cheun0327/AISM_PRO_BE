package com.upvote.aismpro.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "option_keyword_trees")
@Entity
public class OptionKeywordNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "node_id")
    private Long nodeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id", nullable = false)
    private OptionKeyword keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_node_id")
    private OptionKeywordNode parentNode;

    @Column(name = "path", unique = true)
    private String path;

    @Column(name = "depth", nullable = false)
    private Integer depth;

    @Builder
    public OptionKeywordNode(
            Genre genre,
            OptionKeyword keyword,
            OptionKeywordNode parentNode,
            String path,
            Integer depth
    ) {
        this.genre = genre;
        this.keyword = keyword;
        this.parentNode = parentNode;
        this.path = path;
        this.depth = depth;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
