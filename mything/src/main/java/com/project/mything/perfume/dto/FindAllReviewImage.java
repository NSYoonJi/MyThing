package com.project.mything.perfume.dto;

import com.project.mything.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * packageName    : com.project.mything.perfume.dto fileName       : FindAllReviewImageResponse
 * author         : hagnoykmik date           : 2023-03-27 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2023-03-27        hagnoykmik       최초
 * 생성
 */
@AllArgsConstructor
@Data
@Builder
public class FindAllReviewImage {
  private String imgURL;
  private Long likeCnt;

  public static FindAllReviewImage create(Review review) {
    FindAllReviewImage findAllReviewImage = FindAllReviewImage.builder()
        .imgURL(review.getReviewImage().getImage())
        .likeCnt(review.getReviewImage().getLikeCnt())
        .build();
    return findAllReviewImage;
  }

}
