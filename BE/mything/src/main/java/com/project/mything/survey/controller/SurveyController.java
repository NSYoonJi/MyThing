package com.project.mything.survey.controller;

import com.project.mything.survey.dto.FindSurveyResponse;
import com.project.mything.survey.dto.FindSurveyResultResponse;
import com.project.mything.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * packageName    : com.project.mything.survey.controller fileName       : SurveyController author
 * : hagnoykmik date           : 2023-03-30 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-03-30        hagnoykmik       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @GetMapping("survey/{nowpage}")
    public ResponseEntity<FindSurveyResponse> findSurvey(@PathVariable("nowpage") Integer pageNo) {

        FindSurveyResponse findSurvey = surveyService.findSurvey(pageNo);
        return ResponseEntity.ok().body(findSurvey);
    }

    @GetMapping("/profile/survey")
    public List<FindSurveyResultResponse> findSurveyResults(@RequestHeader("Authorization") String token) {

        List<FindSurveyResultResponse> findSurveyResults = surveyService.findSurveyResults(token);
        return findSurveyResults;
    }
}
