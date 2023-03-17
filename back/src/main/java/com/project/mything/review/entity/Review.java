package com.project.mything.review.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.mything.member.entity.Member;
import com.project.mything.perfume.entity.*;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @NotNull
    private String season;

    @Enumerated(EnumType.STRING)
    private Preference preference;

    @Enumerated(EnumType.STRING)
    private Longevity longevity;

    @Enumerated(EnumType.STRING)
    private Sillage sillage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "kakao_id")
    private Member member;

    @OneToOne(mappedBy = "review", fetch = FetchType.LAZY)
    private ReviewImage reviewImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "perfume_id")
    private Perfume perfume;

    public void addMember(Member member) {
        if(this.member!=null){
            this.member.getReviewList().remove(this);
        }
        this.member = member;
        member.getReviewList().add(this);
    }

    public void addPerfume(Perfume perfume) {
        if(this.perfume!=null){
            this.perfume.getReviewList().remove(this);
        }
        this.perfume = perfume;
        perfume.getReviewList().add(this);
    }

}
