package com.project.mything.perfume.service;

import com.project.mything.perfume.dto.FindPerfumeResponse;
import com.project.mything.perfume.entity.Perfume;
import com.project.mything.perfume.repository.PerfumeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.project.mything.perfume.service fileName       : PerfumeServiceImpl author
 *       : SSAFY date           : 2023-03-24 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-03-24        SSAFY       최초 생성
 */

@Transactional
@Service
@RequiredArgsConstructor
public class PerfumeServiceImpl implements PerfumeService {

  private final PerfumeRepository perfumeRepository;

  @Override
  public FindPerfumeResponse findById(Long perfumeId) {

    // perfumeId로 향수 찾기
    Optional<Perfume> perfume = perfumeRepository.findById(perfumeId);
    if (!perfume.isPresent()) throw new IllegalStateException("향수가 존재하지 않습니다.");
    Perfume findPerfume = perfume.get();
    
    // todo: 조회수
    //    Long cnt = findPerfume.getPerfumeDetail().getViewCnt() + 1;
    
    FindPerfumeResponse findPerfumeResponse = FindPerfumeResponse.create(findPerfume);
    return findPerfumeResponse;
  }
}
