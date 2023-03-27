package com.project.mything.perfume.repository;

import com.project.mything.perfume.entity.Perfume;
import com.project.mything.perfume.entity.PerfumeDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.project.mything.perfume.repository fileName       : PerfumeDetailRepository
 * author         : hagnoykmik date           : 2023-03-25 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2023-03-25        hagnoykmik       최초
 * 생성
 */
public interface PerfumeDetailRepository extends JpaRepository<PerfumeDetail, Long> {
}
