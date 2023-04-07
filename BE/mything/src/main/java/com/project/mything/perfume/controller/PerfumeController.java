package com.project.mything.perfume.controller;

import com.project.mything.perfume.dto.*;
import com.project.mything.perfume.service.PerfumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : com.project.mything.perfume.controller fileName       : PerfumeController author
 *         : hagnoykmik date           : 2023-03-24 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-03-24        hagnoykmik       최초 생성
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class PerfumeController {

  private final PerfumeService perfumeService;

  /**
   * 향수 정보 조회
   */
  @GetMapping("/perfume/{perfumeId}")
  public ResponseEntity<FindPerfumeResponse> findPerfume(@PathVariable("perfumeId") Long perfumeId) {
    FindPerfumeResponse findPerfumeResponse = perfumeService.findPerfume(perfumeId);
    return ResponseEntity.ok().body(findPerfumeResponse);
  }

  /**
   * 인기 향수 목록 조회
   */
  @GetMapping("recommend/popular")
  public ResponseEntity<List<FindAllPopularPerfumeResponse>> findPopularPerFume() {
//    List<FindAllPopularPerfumeResponse> allPopularPerfume = perfumeService.findAllPopularPerfume();
    List<FindAllPopularPerfumeResponse> allPopularPerfume = perfumeService.findTop12ByOrderByViewCntDesc();
    return ResponseEntity.ok().body(allPopularPerfume);
  }


  /**
   * 향수 리뷰 이미지 조회
   */
  @GetMapping("/perfume/review-img/{reviewImageId}")
  public ResponseEntity<FindReviewImageResponse> findReviewImage(@RequestHeader("Authorization")String token, @PathVariable("reviewImageId") Long reviewImageId) {
    // todo: memberId
//    Long memberId = 2323L;
    FindReviewImageResponse findReviewImage = perfumeService.findReviewImage(token, reviewImageId);
    return ResponseEntity.ok().body(findReviewImage);
  }

  /**
   * 향수 리뷰 이미지 좋아요
   */
  @PatchMapping("/perfume/review-img/like")
  public ResponseEntity<IsLikeResponse> isLike(@RequestHeader("Authorization")String token, @RequestBody IsLikeRequest request) {
    // todo: memberId
//    Long memberId = 2323L;
    IsLikeResponse like = perfumeService.isLike(token, request);
    return ResponseEntity.ok().body(like);
  }

  /*
   * 향수 검색
   */

  @GetMapping("/perfume/search/{perfume}")
  public ResponseEntity<List<SearchPerfumeResponse>> searchPerfume(@PathVariable String perfume){
    List<SearchPerfumeResponse> searchPerfumeResponses = perfumeService.searchPerfume(perfume);
    return ResponseEntity.status(HttpStatus.OK).body(searchPerfumeResponses);
  }

  /*
   * 연령 향수 조회
   */

  @GetMapping("/perfume/age")
  public ResponseEntity<List<SearchPerfumeByAgeResponse>> searchPerfumeByAge(){
    List<SearchPerfumeByAgeResponse> searchPerfumeByAgeResponses = perfumeService.searchPerfumeByAge();
    return ResponseEntity.status(HttpStatus.OK).body(searchPerfumeByAgeResponses);
  }

}
