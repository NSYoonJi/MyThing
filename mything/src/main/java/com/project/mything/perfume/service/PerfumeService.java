package com.project.mything.perfume.service;

import com.project.mything.perfume.dto.FindAllPopularPerfumeResponse;
import com.project.mything.perfume.dto.FindPerfumeResponse;
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
  List<FindAllPopularPerfumeResponse> findAllPopularPerfume();
}
