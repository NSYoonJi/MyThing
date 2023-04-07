package com.project.mything.review.dto;

import com.project.mything.review.entity.Review;
import lombok.Data;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Data
public class ReadReviewResponse {
    private Long reviewId;
    private Long perfumeId;
    private String perfumeName;
    private String brand;
    private String perfumeImgUrl;
    private String reviewImgUrl;
    private List<String> season;
    private String preference;
    private String longevity;
    private String sillage;

    public ReadReviewResponse(Review review) {
        reviewId = review.getId();
        perfumeId = review.getPerfume().getId();
        perfumeName = review.getPerfume().getKoName();
        brand = review.getPerfume().getKoBrand();
        perfumeImgUrl = review.getPerfume().getImgURL();
        reviewImgUrl = review.getReviewImage().getImage();
        season = Arrays.asList(review.getSeason().split(","));
        preference = String.valueOf(review.getPreference());
        longevity = String.valueOf(review.getLongevity());
        sillage = String.valueOf(review.getSillage());
    }
}
