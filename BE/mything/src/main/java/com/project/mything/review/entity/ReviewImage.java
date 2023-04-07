package com.project.mything.review.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review_image")
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_image_id")
    private Long id;

    private String image;

    @NotNull
    @Column(name = "like_cnt")
    private Long likeCnt;

    @OneToOne(mappedBy = "reviewImage", fetch = FetchType.LAZY)
    @JsonIgnore
    private Review review;

    //==생성 메서드==//
    public static ReviewImage createReviewImage(String image, Long likeCnt) {
        ReviewImage reviewImage = new ReviewImage(image, likeCnt);

        return reviewImage;
    }

    private ReviewImage(String image, Long likeCnt) {
        this.image = image;
        this.likeCnt = likeCnt;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    //==비즈니스 로직==//
    /**
     * 좋아요 수 증가
     */
    public Long addLikeCnt() {
        this.likeCnt++;
        return this.likeCnt;
    }

    /**
     * 좋아요 수 감소
     */
    public Long removeLikeCnt() {
        this.likeCnt--;
        return this.likeCnt;
    }

    public void updateImage(String image) {
        this.image = image;
    }
}
