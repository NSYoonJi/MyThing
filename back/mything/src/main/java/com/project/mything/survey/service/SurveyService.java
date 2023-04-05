package com.project.mything.survey.service;

import com.project.mything.survey.dto.FindSurveyResponse;
import com.project.mything.survey.dto.FindSurveyResultResponse;

import java.util.List;

/**
 * packageName    : com.project.mything.survey.service fileName       : SurveyService author
 * : hagnoykmik date           : 2023-03-30 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-03-30        hagnoykmik       최초 생성
 */
public interface SurveyService {

  FindSurveyResponse findSurvey(Integer pageNo);

  List<FindSurveyResultResponse> findSurveyResults(String token);
}
