package com.project.mything.perfume.repository;

import com.project.mything.perfume.entity.Perfume;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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
    List<Perfume> findByIdIn(List<Long> id);

    // MySQL의 Full-Text Search 기능을 이용하여 검색어에 해당하는 향수 정보를 가져오는 쿼리
    @Query(value = "SELECT * FROM perfume WHERE MATCH(ko_name, ko_brand, name, brand) AGAINST(?1 IN BOOLEAN MODE) LIMIT 20", nativeQuery = true)
    List<Perfume> findBySearchTerm(String searchTerm);

    // FullText Search 결과가 없을때 LIKE 검색
    @Query(value = "SELECT * FROM perfume WHERE CONCAT(ko_name, ko_brand, name, brand) LIKE %:perfume% LIMIT 20", nativeQuery = true)
    List<Perfume> findBySearchTermDefault(@Param("perfume") String perfume);

    // 검색어를 받아서 Full-Text Search로 검색을 수행하는 메소드
    default List<Perfume> search(String searchTerm) {
        // 검색어를 공백으로 분리하여 배열로 만듦
        String[] words = searchTerm.trim().split("\\s+");

        // Full-Text Search 쿼리를 작성하는 데 사용할 StringBuilder
        StringBuilder queryBuilder = new StringBuilder();

        // 검색어를 하나씩 처리하며 Full-Text Search 쿼리를 작성함
        for (String word : words) {
            if (!word.isEmpty()) {
                // Full-Text Search 쿼리에서 각 검색어는 +검색어* 형식으로 표현됨
                queryBuilder.append("+").append(word).append("* ");
            }
        }

        // 검색어를 이용한 Full-Text Search 쿼리를 가져옴
        String query = queryBuilder.toString().trim();
        List<Perfume> result = findBySearchTerm(query);
        return result;
    }
}
