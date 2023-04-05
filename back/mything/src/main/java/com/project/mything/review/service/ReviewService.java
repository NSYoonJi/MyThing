package com.project.mything.review.service;

import com.project.mything.review.dto.*;
import com.project.mything.review.entity.Review;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ReviewService {
    CreateReviewResponse review(String token, CreateReviewRequest request, MultipartFile img) throws IOException;
    List<ReadReviewResponse> readReviews(String token);
    ReadReviewResponse readReview(Long reviewId);
    UpdateReviewResponse updateReview(Long reviewId, UpdateReviewRequest request, MultipartFile img) throws IOException;
    void updatePerfumeDetail(Long perfumeId);

}
