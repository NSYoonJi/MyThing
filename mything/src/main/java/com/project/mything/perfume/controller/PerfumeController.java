package com.project.mything.perfume.controller;

import com.project.mything.perfume.dto.FindPerfumeResponse;
import com.project.mything.perfume.entity.Perfume;
import com.project.mything.perfume.service.PerfumeService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.project.mything.perfume.controller fileName       : PerfumeController author
 *         : SSAFY date           : 2023-03-24 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-03-24        SSAFY       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class PerfumeController {

  private final PerfumeService perfumeService;

  @GetMapping("/perfume/{perfumeId}")
  public ResponseEntity<FindPerfumeResponse> findPerfumeById(@PathVariable("perfumeId") Long perfumeId) {
    FindPerfumeResponse findPerfumeResponse = perfumeService.findById(perfumeId);
    return ResponseEntity.ok().body(findPerfumeResponse);
  }

}
