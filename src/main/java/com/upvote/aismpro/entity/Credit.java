package com.upvote.aismpro.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Table(name = "credits")
@NoArgsConstructor
@Entity
public class Credit extends BaseEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_id")
    private Long creditId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "credit", nullable = false)
    private Long credit;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedDate;

    @Builder
    public Credit(User user, Long credit, LocalDateTime createdDate) {
        this.user = user;
        this.credit = credit;
        this.createdDate = createdDate;
    }

    public void updateCredit(Long credit) {
        this.credit = credit;
    }
}
