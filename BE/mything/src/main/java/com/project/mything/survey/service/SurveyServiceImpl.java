package com.project.mything.survey.service;

import com.project.mything.auth.jwt.service.JwtService;
import com.project.mything.survey.dto.FindSurveyResponse;
import com.project.mything.survey.dto.FindSurveyResultResponse;
import com.project.mything.survey.dto.surveyquestion.SurveyQuestion;

import java.util.*;
import java.util.stream.Collectors;

import com.project.mything.survey.entity.Survey;
import com.project.mything.survey.entity.SurveyResult;
import com.project.mything.survey.repository.SurveyRepository;
import com.project.mything.survey.repository.SurveyResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.project.mything.survey.service fileName       : SurveyImpl author         :
 * hagnoykmik date           : 2023-03-30 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-03-30        hagnoykmik       최초 생성
 */
@Service
@Transactional
@RequiredArgsConstructor
@ToString
public class SurveyServiceImpl implements SurveyService {

    private final SurveyResultRepository surveyResultRepository;
    private final SurveyRepository surveyRepository;
    private final JwtService jwtService;

    SurveyQuestion surveyQuestion = new SurveyQuestion();

    @Override
    public FindSurveyResponse findSurvey(Integer pageNo) {

        HashMap<String, String[]> questions = surveyQuestion.questions().get(pageNo);

        String q = questions.get("q")[0];
        String[] a = questions.get("a");
        String[] b = questions.get("b");
        String[] c = questions.get("c");
        String[] d = questions.get("d");


        // 이미지 찾기
        String sPageNo = String.valueOf(pageNo);
        List<Survey> imgs = surveyRepository.findByName(sPageNo);
        Map<String, String> img = new HashMap<>();
        for (int i = 0; i < imgs.size(); i++) {
            String name = pageNo + "-" + String.valueOf((char) (97 + i));
            String imgUrl = imgs.get(i).getImage();
            img.put(name, imgUrl);
        }
        FindSurveyResponse findSurveyResponse = FindSurveyResponse.create(q, a, b, c, d, img);
        return findSurveyResponse;
    }

    @Override
    public List<FindSurveyResultResponse> findSurveyResults(String token) {
        Long memberId = jwtService.getUserIdFromToken(token);
        List<SurveyResult> surveyResults = surveyResultRepository.findAllByMember_Id(memberId);

        List<FindSurveyResultResponse> result = surveyResults.stream()
                .map(r -> new FindSurveyResultResponse(r))
                .collect(Collectors.toList());

        return result;
    }
}
