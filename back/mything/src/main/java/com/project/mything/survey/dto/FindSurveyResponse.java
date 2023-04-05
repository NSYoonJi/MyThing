package com.project.mything.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * packageName    : com.project.mything.survey.dto fileName       : FindSurveyResponse author
 * : hagnoykmik date           : 2023-03-30 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-03-30        hagnoykmik       최초 생성
 */
@AllArgsConstructor
@Data
@Builder
public class FindSurveyResponse {
    private String question;
    private String[] answerA;
    private String[] answerB;
    private String[] answerC;
    private String[] answerD;
    private Map<String,String> images;
    public static FindSurveyResponse create(String q, String[] a, String[] b, String[] c, String[] d, Map<String, String> images) {
        FindSurveyResponse findSurveyResponse = FindSurveyResponse.builder()
                .question(q)
                .answerA(a)
                .answerB(b)
                .answerC(c)
                .answerD(d)
                .images(images)
                .build();
        return findSurveyResponse;
    }
}
