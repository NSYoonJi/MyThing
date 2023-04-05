package com.project.mything.review.cotroller;

import com.project.mything.review.dto.*;
import com.project.mything.review.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;

    // 리뷰 등록
    @PostMapping(value = "/profile/review")
    public CreateReviewResponse setReview(@RequestHeader("Authorization") String token, @RequestPart CreateReviewRequest request, @RequestPart(required = false) MultipartFile image) throws IOException {

        CreateReviewResponse result = reviewService.review(token, request, image);
        return result;
    }

    // 리뷰 목록 조회
    @GetMapping("/profile/review")
    public Results readReviews(@RequestHeader("Authorization") String token) {
        List<ReadReviewResponse> result = reviewService.readReviews(token);

        return new Results(result);
    }

    // 리뷰 상세 조회
    @GetMapping("/profile/review/{reviewId}")
    public ReadReviewResponse readReview(@PathVariable("reviewId") Long reviewId) {
        ReadReviewResponse result = reviewService.readReview(reviewId);

        return result;
    }

    // 리뷰 수정
    @PatchMapping("/profile/review/{reviewId}")
    public UpdateReviewResponse updateReview(@PathVariable("reviewId") Long reviewId, @RequestPart UpdateReviewRequest request, @RequestPart(required = false) MultipartFile image) throws IOException {
        UpdateReviewResponse result = reviewService.updateReview(reviewId, request, image);

        return result;
    }

    @Data
    @AllArgsConstructor
    static class Results<T> {
        private T data;
    }
}
