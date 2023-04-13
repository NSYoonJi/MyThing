package com.project.mything.perfume.controller;

import com.project.mything.auth.jwt.service.JwtService;
import com.project.mything.perfume.dto.FindNotesByCategoryResponse;
import com.project.mything.perfume.dto.PreferNotesRequest;
import com.project.mything.perfume.dto.RecommendPerfumesResponse;
import com.project.mything.perfume.dto.UserServeyRequest;
import com.project.mything.perfume.service.RecommendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
//@RequestMapping("/recommend")
public class RecommendController {
    private final RecommendService recommendService;
    private final JwtService jwtService;

    /*
     *   클릭 기반 추천
     */
    @GetMapping("/recommend/survey/{perfumeId}")
    public ResponseEntity<List<RecommendPerfumesResponse>> findPerfumeByClick(@PathVariable Long perfumeId) throws IOException {
        List<RecommendPerfumesResponse> perfumeByClick = recommendService.findPerfumeByClick(perfumeId);
        return ResponseEntity.status(HttpStatus.OK).body(perfumeByClick);
    }

    /*
     *   리뷰 기반 추천
     */
    @GetMapping("/recommend/recommend-review")
    public ResponseEntity<List<RecommendPerfumesResponse>> findPerfumeByReview(@RequestHeader("Authorization") String token) throws IOException {
        Long mid = jwtService.getUserIdFromToken(token);
        List<RecommendPerfumesResponse> perfumeByClick = recommendService.findPerfumeByReview(token, mid);
        return ResponseEntity.status(HttpStatus.OK).body(perfumeByClick);
    }

    /*
     * 설문 기반 추천
     */

    @GetMapping("/recommend/survey")
    public ResponseEntity<List<RecommendPerfumesResponse>> findPerfumeBySurvey(@RequestHeader("Authorization") String token) throws IOException {

        List<RecommendPerfumesResponse> perfumeByClick = recommendService.findPerfumeBySurvey(token);
        return ResponseEntity.status(HttpStatus.OK).body(perfumeByClick);
    }

    /*
     *   설문 기반 반대 추천
     */
    @GetMapping("/recommend/survey-reverse")
    public ResponseEntity<List<RecommendPerfumesResponse>> findPerfumeBySurveyReverse(@RequestHeader("Authorization") String token) throws IOException {
        List<RecommendPerfumesResponse> perfumeBySurveyReverse = recommendService.findPerfumeBySurveyReverse(token);
        return ResponseEntity.status(HttpStatus.OK).body(perfumeBySurveyReverse);
    }

    /*
     *   선호향 추천
     */
    @PostMapping("/recommend/prefer")
    public ResponseEntity<List<RecommendPerfumesResponse>> findPerfumeByPrefer(@RequestBody PreferNotesRequest preferNotesRequest) throws IOException {
        List<RecommendPerfumesResponse> perfumeBySurveyReverse = recommendService.findPerfumeByPrefer(preferNotesRequest);
        return ResponseEntity.status(HttpStatus.OK).body(perfumeBySurveyReverse);
    }

    /*
     *   유저 선호향 추천
     */

    @GetMapping("/recommend/prefer")
    public ResponseEntity<List<RecommendPerfumesResponse>> findUserPerfumeByPrefer(@RequestHeader("Authorization") String token) throws IOException {
        List<RecommendPerfumesResponse> perfumeBySurveyReverse = recommendService.findUserPerfumeByPrefer(token);
        return ResponseEntity.status(HttpStatus.OK).body(perfumeBySurveyReverse);
    }

    /*
     * 등록된 유저가 취향 조사 후 저장
     */
    @PostMapping("/recommend/user-survey")
    public ResponseEntity<RecommendPerfumesResponse> findPerfumeByUserServey(@RequestHeader("Authorization") String token, @RequestBody UserServeyRequest userServeyRequest) throws IOException {
        RecommendPerfumesResponse perfumeByUserSurvey = recommendService.findPerfumeByUserSurvey(token, userServeyRequest);
        return ResponseEntity.status(HttpStatus.OK).body(perfumeByUserSurvey);
    }

    /*
     * 게스트가 취향 조사 후 조사 결과 프론트에서 세션에 저장 후 회원가입 후 등록
     */
    @PostMapping("/guest/guest-survey")
    public ResponseEntity<RecommendPerfumesResponse> doSurvey(@RequestBody UserServeyRequest userServeyRequest) throws IOException {
        RecommendPerfumesResponse PerfumeByGuestSurvey = recommendService.findPerfumeByGuestSurvey(userServeyRequest);
        return ResponseEntity.status(HttpStatus.OK).body(PerfumeByGuestSurvey);
    }

    /*
     * 카테고리 클릭시 노트 조회
     */
    @GetMapping("/guest/recommend/{category}")
    public ResponseEntity<List<FindNotesByCategoryResponse>> findNoteByCategory(@PathVariable String category) {
        List<FindNotesByCategoryResponse> notesByCategory = recommendService.findNotesByCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body(notesByCategory);
    }

}
