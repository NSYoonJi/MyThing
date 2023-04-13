package com.project.mything.review.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.project.mything.auth.jwt.service.JwtService;
import com.project.mything.member.entity.Member;
import com.project.mything.member.repository.MemberRepository;
import com.project.mything.perfume.entity.Perfume;
import com.project.mything.perfume.entity.PerfumeDetail;
import com.project.mything.perfume.repository.PerfumeDetailRepository;
import com.project.mything.perfume.repository.PerfumeRepository;
import com.project.mything.review.dto.*;
import com.project.mything.review.entity.Review;
import com.project.mything.review.entity.ReviewImage;
import com.project.mything.review.repository.ReviewImageRepository;
import com.project.mything.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final PerfumeRepository perfumeRepository;
    private final PerfumeDetailRepository perfumeDetailRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final JwtService jwtService;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Autowired
    private AmazonS3 amazonS3;

    //향수 디테일 수정
    @Override
    @Transactional
    public void updatePerfumeDetail(Long perfumeId) {
        PerfumeDetail findPerfumeDetail = perfumeDetailRepository.findByPerfume_Id(perfumeId);
    }

    //리뷰 생성
    @Override
    @CacheEvict(key = "#memberId", cacheNames = "recommendPerfumesResponse")
    @Transactional
    public CreateReviewResponse review(String token, CreateReviewRequest request, MultipartFile image, Long memberId) throws IOException {
        Optional<Member> member = memberRepository.findById(memberId);
        Member findMember = member.orElseThrow(() -> new IllegalStateException("회원이 존재하지 않습니다."));

        Optional<Perfume> perfume = perfumeRepository.findById(request.getPerfumeId());
        Perfume findPerfume = perfume.orElseThrow(() -> new IllegalStateException("향수가 존재하지 않습니다"));

        Optional<PerfumeDetail> perfumeDetail = Optional.ofNullable(perfumeDetailRepository.findByPerfume_Id(request.getPerfumeId()));
        PerfumeDetail findPerfumeDetail = perfumeDetail.orElseThrow(() -> new IllegalStateException("향수 상세 정보가 없습니다"));

        // 리뷰 디테일 정보 업데이트 (증가)
        findPerfumeDetail.updateReviewDetail(request.getSeason(), request.getPreference(), true);

        // 이미지 파일 유무에 따라 분기처리
        if (image == null || image.isEmpty()) {
            // 이미지 파일이 존재하지 않을 때
            ReviewImage reviewImage = ReviewImage.createReviewImage(null, 0L);
            Review review = Review.createReview(findMember, findPerfume, reviewImage,
                    String.join(",", request.getSeason()), request.getPreference(), request.getLongevity(), request.getSillage());

            reviewRepository.save(review);

            return new CreateReviewResponse(review.getId());

        } else {
            // 이미지 파일이 존재할 때
            String s3FileName = UUID.randomUUID() + "-" + image.getOriginalFilename();
            ObjectMetadata objMeta = new ObjectMetadata();
            objMeta.setContentLength(image.getInputStream().available());
            amazonS3.putObject(bucket, s3FileName, image.getInputStream(), objMeta);
            amazonS3.setObjectAcl(bucket, s3FileName, CannedAccessControlList.PublicRead);
            ReviewImage reviewImage = ReviewImage.createReviewImage(amazonS3.getUrl(bucket, s3FileName).toString(), 0L);
            Review review = Review.createReview(findMember, findPerfume, reviewImage,
                    String.join(",", request.getSeason()), request.getPreference(), request.getLongevity(), request.getSillage());

            reviewRepository.save(review);

            return new CreateReviewResponse(review.getId());
        }
    }

    // 리뷰 목록 조회
    @Override
    public List<ReadReviewResponse> readReviews(String token) {
        Long memberId = jwtService.getUserIdFromToken(token);
        Optional<Member> member = memberRepository.findById(memberId);
        Member findMember = member.orElseThrow(() -> new IllegalStateException("회원이 존재하지 않습니다."));

        List<Review> reviews = findMember.getReviewList();

        List<ReadReviewResponse> result = reviews.stream()
                .map(r -> new ReadReviewResponse(r))
                .collect(Collectors.toList());

        return result;
    }

    // 리뷰 조회
    @Override
    @Transactional
    public ReadReviewResponse readReview(Long reviewId) {

        Optional<Review> review = reviewRepository.findById(reviewId);
        Review findReview = review.orElseThrow(() -> new IllegalStateException("존재하지 않는 리뷰입니다"));

        return new ReadReviewResponse(findReview);
    }

    // 리뷰 수정
    @Override
    @Transactional
    public UpdateReviewResponse updateReview(Long reviewId, UpdateReviewRequest request, MultipartFile image) throws IOException {

        Optional<Review> review = reviewRepository.findById(reviewId);
        Review findReview = review.orElseThrow(() -> new IllegalStateException("존재하지 않는 리뷰입니다."));

        Optional<PerfumeDetail> perfumeDetail = Optional.ofNullable(perfumeDetailRepository.findByPerfume_Id(findReview.getPerfume().getId()));
        PerfumeDetail findPerfumeDetail = perfumeDetail.orElseThrow(() -> new IllegalStateException("향수 상세 정보가 없습니다"));

        // 기존 리뷰 디테일 정보 업데이트 (감소)
        findPerfumeDetail.updateReviewDetail(
                Arrays.asList(findReview.getSeason().split(",")),
                findReview.getPreference(), false
        );

        // 새로운 리뷰 디테일 정보 업데이트 (증가)
        findPerfumeDetail.updateReviewDetail(request.getSeason(), request.getPreference(), true);

        // 리뷰 정보 업데이트
        findReview.updateReview(String.join(",", request.getSeason()), request.getPreference(), request.getLongevity(), request.getSillage());


        if (!(image == null || image.isEmpty())) {
            ReviewImage reviewImage = reviewImageRepository.findByReview_Id(reviewId);
            String s3FileName = UUID.randomUUID() + "-" + image.getOriginalFilename();
            ObjectMetadata objMeta = new ObjectMetadata();
            objMeta.setContentLength(image.getInputStream().available());
            amazonS3.putObject(bucket, s3FileName, image.getInputStream(), objMeta);
            amazonS3.setObjectAcl(bucket, s3FileName, CannedAccessControlList.PublicRead);
            reviewImage.updateImage(amazonS3.getUrl(bucket, s3FileName).toString());
        }

        return new UpdateReviewResponse(findReview.getId());
    }


}
