package com.project.mything.perfume.service;

import com.project.mything.perfume.dto.FindNotesByCategoryResponse;
import com.project.mything.perfume.dto.PreferNotesRequest;
import com.project.mything.perfume.dto.RecommendPerfumesResponse;
import com.project.mything.perfume.dto.UserServeyRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface RecommendService {
    List<RecommendPerfumesResponse> findPerfumeByClick(Long perfumeId) throws IOException;
    List<RecommendPerfumesResponse> findPerfumeBySurvey(String token) throws IOException;
    List<RecommendPerfumesResponse> findPerfumeBySurveyReverse(String token) throws IOException;
    List<RecommendPerfumesResponse> findPerfumeByReview(String token, Long id) throws IOException;
    List<RecommendPerfumesResponse> findPerfumeByPrefer(PreferNotesRequest preferNotesRequest) throws IOException;
    List<RecommendPerfumesResponse> findUserPerfumeByPrefer(String token) throws IOException;
    RecommendPerfumesResponse findPerfumeByUserSurvey(String token, UserServeyRequest userServeyRequest) throws IOException;
    RecommendPerfumesResponse findPerfumeByGuestSurvey(UserServeyRequest userServeyRequest) throws IOException;
    List<FindNotesByCategoryResponse> findNotesByCategory(String category);

}
