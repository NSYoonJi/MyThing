package com.project.mything.review.repository;

import com.project.mything.review.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.project.mything.perfume.repository fileName       : ReviewImageRepository
 * author         : SSAFY date           : 2023-03-27 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2023-03-27        SSAFY       최초
 * 생성
 */
public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
    ReviewImage findByReview_Id(Long reviewId);
}
