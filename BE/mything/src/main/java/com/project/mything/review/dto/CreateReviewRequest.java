package com.project.mything.review.dto;

import com.project.mything.review.entity.Longevity;
import com.project.mything.review.entity.Preference;
import com.project.mything.review.entity.Sillage;
import lombok.Data;

import java.util.List;


@Data
public class CreateReviewRequest {

    private Long perfumeId;
    private List<String> season;
    private Preference preference;
    private Longevity longevity;
    private Sillage sillage;

}
