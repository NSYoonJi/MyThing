package com.project.mything.perfume.repository;

import com.project.mything.perfume.entity.Perfume;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.project.mything.perfume.repository
 * fileName       : PerfumeRepository
 * author         : hagnoykmik
 * date           : 2023-03-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-03-24        hagnoykmik       최초 생성
 */
public interface PerfumeRepository extends JpaRepository<Perfume, Long> {
}
