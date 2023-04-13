package com.project.mything.perfume.dto;

import com.project.mything.review.entity.ReviewImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Lob;

/**
 * packageName    : com.project.mything.perfume.dto fileName       : FindReviewImageResponse author
 * : SSAFY date           : 2023-03-27 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-03-27        SSAFY       최초 생성
 */
@AllArgsConstructor
@Data
@Builder
public class FindReviewImageResponse {

    @Lob
    private String reviewImgUrl;
    private Long likeCnt;
    private Boolean isLike;

    public static FindReviewImageResponse create(ReviewImage reviewImage, Boolean isLike) {
        FindReviewImageResponse findReviewImageResponse = FindReviewImageResponse.builder()
                .reviewImgUrl(reviewImage.getImage())
                .likeCnt(reviewImage.getLikeCnt())
                .isLike(isLike)
                .build();
        return findReviewImageResponse;
    }
}
