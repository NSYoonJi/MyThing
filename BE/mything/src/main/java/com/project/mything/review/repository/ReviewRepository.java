package com.project.mything.review.repository;

import com.project.mything.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findByMember_Id(Long memberId);

    List<Review> findByMemberId(Long memberId);
}
