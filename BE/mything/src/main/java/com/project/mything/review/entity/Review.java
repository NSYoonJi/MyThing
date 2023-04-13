package com.project.mything.review.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.mything.member.entity.Member;
import com.project.mything.perfume.entity.Perfume;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "reviewImage_id")
    private ReviewImage reviewImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "perfume_id")
    private Perfume perfume;

    //==생성 메서드==//
    public static Review createReview(Member member, Perfume perfume, ReviewImage reviewImage,
                                      String season, Preference preference, Longevity longevity, Sillage sillage) {
        Review review = new Review(season, preference, longevity, sillage);
        review.addMember(member);
        review.addReviewImg(reviewImage);
        review.addPerfume(perfume);

        return review;
    }

    private Review(String season, Preference preference, Longevity longevity, Sillage sillage) {
        this.season = season;
        this.preference = preference;
        this.longevity = longevity;
        this.sillage = sillage;
    }


    //==연관관계 편의 메서드==//
    public void addMember(Member member) {
        if (this.member != null) {
            this.member.getReviewList().remove(this);
        }
        this.member = member;
        member.getReviewList().add(this);
    }

    public void addPerfume(Perfume perfume) {
        if (this.perfume != null) {
            this.perfume.getReviewList().remove(this);
        }
        this.perfume = perfume;
        perfume.getReviewList().add(this);
    }

    public void addReviewImg(ReviewImage reviewImage) {
        this.reviewImage = reviewImage;
        reviewImage.setReview(this);
    }

    public void updateReview(String season, Preference preference, Longevity longevity, Sillage sillage) {
        this.season = season;
        this.preference = preference;
        this.longevity = longevity;
        this.sillage = sillage;
    }
}
