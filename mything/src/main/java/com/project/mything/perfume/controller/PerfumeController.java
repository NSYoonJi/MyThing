package com.project.mything.perfume.controller;

import com.project.mything.perfume.dto.FindAllPopularPerfumeResponse;
import com.project.mything.perfume.dto.FindPerfumeResponse;
import com.project.mything.perfume.service.PerfumeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.project.mything.perfume.controller fileName       : PerfumeController author
 *         : hagnoykmik date           : 2023-03-24 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-03-24        hagnoykmik       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class PerfumeController {

  private final PerfumeService perfumeService;

  // 향수 정보 조회
  @GetMapping("/perfume/{perfumeId}")
  public ResponseEntity<FindPerfumeResponse> findPerfume(@PathVariable("perfumeId") Long perfumeId) {
    FindPerfumeResponse findPerfumeResponse = perfumeService.findPerfume(perfumeId);
    return ResponseEntity.ok().body(findPerfumeResponse);
  }

  // 인기 향수 목록 조회
  @GetMapping("recommend/popular")
  public ResponseEntity<List<FindAllPopularPerfumeResponse>> findPopularPerFume() {
    List<FindAllPopularPerfumeResponse> allPopularPerfume = perfumeService.findAllPopularPerfume();
    return ResponseEntity.ok().body(allPopularPerfume);
  }



}
