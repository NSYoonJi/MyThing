package com.project.mything.perfume.service;

import com.project.mything.perfume.dto.FindPerfumeResponse;
import com.project.mything.perfume.entity.Perfume;
import com.project.mything.perfume.entity.PerfumeDetail;
import com.project.mything.perfume.repository.PerfumeDetailRepository;
import com.project.mything.perfume.repository.PerfumeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.project.mything.perfume.service fileName       : PerfumeServiceImpl author
 *       : hagnoykmik date           : 2023-03-24 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-03-24        hagnoykmik       최초 생성
 */

@Transactional
@Service
@RequiredArgsConstructor
public class PerfumeServiceImpl implements PerfumeService {

  private final PerfumeRepository perfumeRepository;
  private final PerfumeDetailRepository perfumeDetailRepository;

  @Override
  public FindPerfumeResponse findById(Long perfumeId) {

    /**
     * 향수 정보 조회
      */
    Optional<Perfume> perfume = perfumeRepository.findById(perfumeId);
    // 검증
    if (!perfume.isPresent()) throw new IllegalStateException("향수가 존재하지 않습니다.");
    // 비어있지 않다면 객체 생성
    Perfume findPerfume = perfume.get();

    /**
     * 조회수
      */
    Optional<PerfumeDetail> perfumeDetail = perfumeDetailRepository.findById(perfumeId);
    // todo: exception
    if (!perfumeDetail.isPresent()) throw new IllegalStateException("향수가 존재하지 않습니다.");
    Long findPerfumeViewCnt = perfumeDetail.get().updateViewCount(perfumeDetail.get()
        .getViewCnt());

    FindPerfumeResponse findPerfumeResponse = FindPerfumeResponse.create(findPerfume, findPerfumeViewCnt);

    return findPerfumeResponse;
  }
}
