package com.project.mything.survey.dto;

import com.project.mything.survey.entity.SurveyResult;
import lombok.Data;

@Data
public class FindSurveyResultResponse {
    private Long perfumeId;
    private String name;
    private String brand;
    private String date;
    private String imgUrl;

    public FindSurveyResultResponse(SurveyResult surveyResult) {
        perfumeId = surveyResult.getPerfume().getId();
        name = surveyResult.getPerfume().getKoName();
        brand = surveyResult.getPerfume().getKoBrand();
        date = surveyResult.getTime().toString();
        imgUrl = surveyResult.getPerfume().getImgURL();
    }
}
