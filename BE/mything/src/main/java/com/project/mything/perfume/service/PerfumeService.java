package com.project.mything.perfume.service;

import com.project.mything.perfume.dto.*;

import java.util.List;

/**
 * packageName    : com.project.mything.perfume.service
 * fileName       : PerfumeService
 * author         : hagnoykmik
 * date           : 2023-03-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-03-24        hagnoykmik       최초 생성
 */
public interface PerfumeService {
    FindPerfumeResponse findPerfume(Long perfumeId);

    List<FindAllPopularPerfumeResponse> findTop12ByOrderByViewCntDesc();

    FindReviewImageResponse findReviewImage(String token, Long reviewImageId);

    IsLikeResponse isLike(String token, IsLikeRequest request);

    List<SearchPerfumeResponse> searchPerfume(String perfume);

    List<SearchPerfumeByAgeResponse> searchPerfumeByAge();

}

