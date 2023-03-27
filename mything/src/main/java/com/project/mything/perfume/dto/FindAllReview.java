package com.project.mything.perfume.dto;

import com.project.mything.perfume.entity.Longevity;
import com.project.mything.perfume.entity.Preference;
import com.project.mything.perfume.entity.Sillage;
import com.project.mything.review.entity.Review;
import com.sun.istack.NotNull;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class FindAllReview {
  private String season;

  @Enumerated(EnumType.STRING)
  private Preference preference;

  @Enumerated(EnumType.STRING)
  private Longevity longevity;

  @Enumerated(EnumType.STRING)
  private Sillage sillage;

  private String nickname;

  public static FindAllReview create(Review review) {
    FindAllReview findAllReview = FindAllReview.builder()
        .season(review.getSeason())
        .preference(review.getPreference())
        .longevity(review.getLongevity())
        .sillage(review.getSillage())
        .nickname(review.getMember().getMemberProfile().getNickname())
        .build();
    return findAllReview;
  }

}
