package com.project.mything.perfume.service;

import com.project.mything.perfume.dto.FindPerfumeResponse;
import com.project.mything.perfume.entity.Perfume;
import java.util.Optional;

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
  FindPerfumeResponse findById(Long perfumeId);
}
