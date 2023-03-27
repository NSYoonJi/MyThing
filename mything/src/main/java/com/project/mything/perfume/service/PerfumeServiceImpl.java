//package com.project.mything.perfume.service;
//
//import com.project.mything.perfume.dto.FindAllPopularPerfumeResponse;
//import com.project.mything.perfume.dto.FindAllReviewByPerfumeId;
//import com.project.mything.perfume.dto.FindAllReviewImageByPerfumeId;
//import com.project.mything.perfume.dto.FindPerfumeResponse;
//import com.project.mything.perfume.entity.Perfume;
//import com.project.mything.perfume.entity.PerfumeDetail;
//import com.project.mything.perfume.repository.PerfumeDetailRepository;
//import com.project.mything.perfume.repository.PerfumeRepository;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.domain.Sort.Direction;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * packageName    : com.project.mything.perfume.service fileName       : PerfumeServiceImpl author
// *       : hagnoykmik date           : 2023-03-24 description    : ===========================================================
// * DATE              AUTHOR             NOTE -----------------------------------------------------------
// * 2023-03-24        hagnoykmik       최초 생성
// */
//
//@Transactional
//@Service
//@RequiredArgsConstructor
//public class PerfumeServiceImpl implements PerfumeService {
//
//  private final PerfumeRepository perfumeRepository;
//  private final PerfumeDetailRepository perfumeDetailRepository;
//
//  /**
//   * 향수 조회 (단건)
//   */
//  @Override
//  public FindPerfumeResponse findPerfume(Long perfumeId) {
//    // 향수 조회
//    Optional<Perfume> perfume = perfumeRepository.findById(perfumeId);
//    if (!perfume.isPresent()) throw new IllegalStateException("향수가 존재하지 않습니다.");
//    Perfume findPerfume = perfume.get();
////    findPerfume.getNotes()
//
//    // 조회수 update
//    Optional<PerfumeDetail> perfumeDetail = perfumeDetailRepository.findById(perfumeId);
//    // todo:exception
//    if (!perfumeDetail.isPresent()) throw new IllegalStateException("향수가 존재하지 않습니다.");
//    Long findPerfumeViewCnt = perfumeDetail.get().updateViewCount(perfumeDetail.get()
//        .getViewCnt());
//
//    // 향수의 리뷰 리스트를 Dto로 반환
//    List<FindAllReviewByPerfumeId> findReviewList = findPerfume.getReviewList().stream()
//        .map(r -> new FindAllReviewByPerfumeId(r.getSeason(), r.getPreference(), r.getLongevity(), r.getSillage(), r.getMember().getMemberProfile().getNickname()))
//        .collect(Collectors.toList());
//
//    // todo: 향수의 리뷰리스트에서 리뷰 이미지가 있으면 Dto로 반환
//    List<FindAllReviewImageByPerfumeId> findReviewImageList = findPerfume.getReviewList().stream()
//        .map(r -> new FindAllReviewImageByPerfumeId(r.getReviewImage().getImage(),
//            r.getReviewImage().getLikeCnt()))
//        .collect(Collectors.toList());
//
//    FindPerfumeResponse findPerfumeResponse = FindPerfumeResponse.create(findPerfume, findPerfumeViewCnt, findReviewList, findReviewImageList);
//    return findPerfumeResponse;
//  }
//
//  /**
//   * 인기 향수 목록 (조회수)
//   */
//  @Override
//  public List<FindAllPopularPerfumeResponse> findAllPopularPerfume() {
//    // todo:12개로 잘라야함
//    List<PerfumeDetail> popularList = perfumeDetailRepository.findAll(Sort.by(Direction.DESC, "viewCnt"));
//    // DTO로 변환
//    List<FindAllPopularPerfumeResponse> collect = popularList.stream()
//        .map(p -> new FindAllPopularPerfumeResponse(p.getPerfume().getId(), p.getPerfume().getName(),
//            p.getPerfume().getBrand(), p.getPerfume().getImgURL()))
//        .collect(Collectors.toList());
//    return collect;
//  }
//
//}
