package com.project.mything.perfume.dto;

import com.project.mything.review.entity.Longevity;
import com.project.mything.review.entity.Preference;
import com.project.mything.review.entity.Sillage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

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
public class FindAllReviewByPerfumeId {
    private String season;

    @Enumerated(EnumType.STRING)
    private Preference preference;

    @Enumerated(EnumType.STRING)
    private Longevity longevity;

    @Enumerated(EnumType.STRING)
    private Sillage sillage;

    private String nickname;
    private String imgUrl;

}
