package com.project.mything.perfume.service;

import com.project.mything.auth.jwt.service.JwtService;
import com.project.mything.member.entity.Member;
import com.project.mything.member.entity.MemberLike;
import com.project.mything.member.repository.MemberLikeRepository;
import com.project.mything.member.repository.MemberRepository;
import com.project.mything.perfume.dto.*;
import com.project.mything.perfume.entity.Note;
import com.project.mything.perfume.entity.Perfume;
import com.project.mything.perfume.entity.PerfumeDetail;
import com.project.mything.perfume.repository.NoteRepository;
import com.project.mything.perfume.repository.PerfumeDetailRepository;
import com.project.mything.perfume.repository.PerfumeRepository;
import com.project.mything.review.entity.Review;
import com.project.mything.review.repository.ReviewImageRepository;
import com.project.mything.review.entity.ReviewImage;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.project.mything.perfume.service
 * fileName       : PerfumeServiceImpl
 * author         : hagnoykmik
 * date           : 2023-03-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-03-24        hagnoykmik       최초 생성
 */

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PerfumeServiceImpl implements PerfumeService {

  private final PerfumeRepository perfumeRepository;
  private final PerfumeDetailRepository perfumeDetailRepository;
  private final ReviewImageRepository reviewImageRepository;
  private final MemberRepository memberRepository;
  private final MemberLikeRepository memberLikeRepository;
  private final NoteRepository noteRepository;
  private final JwtService jwtService;

  /**
   * 향수 조회
   */
  @Transactional
  @Override
  public FindPerfumeResponse findPerfume(Long perfumeId) {

    // 향수 조회
    Optional<Perfume> perfume = perfumeRepository.findById(perfumeId);
    Perfume findPerfume = perfume.orElseThrow(() -> new IllegalStateException("향수가 존재하지 않습니다."));  // orElse~는 null값이 아니면 알아서 내부의 값을 가지고 와준다(get())

    // 향수 노트 쪼개기
    String[] topNotes = new String[]{};
    String[] middleNotes = new String[]{};
    String[] baseNotes = new String[]{};

    String[] split = (findPerfume.getNotes()).split("], ");
    for (int i = 0; i < split.length; i++) {
      split[i] = split[i].replaceAll("[\\[\\[\\]]", "")
          .replaceAll("[\\]\\]\\]]", "");
    }
    if (findPerfume.getNotes().contains("], [")) {
      topNotes = split[0].split(", ");
      middleNotes = split[1].split(", ");
      baseNotes = split[2].split(", ");
    } else {
      topNotes = split[0].split(", ");
      middleNotes = new String[]{"none"};;
      baseNotes = new String[]{"none"}; ;
    }

    // 향수 노트 이름 찾아오기
    Long[] topNotesByName = Arrays.stream(topNotes).map(s -> Long.parseLong(s.replaceAll("\"", "")))
        .toArray(Long[]::new);
    Long[] middleNotesByName = Arrays.stream(middleNotes).filter(s -> s != "none").map(s -> Long.parseLong(s.replaceAll("\"", "")))
        .toArray(Long[]::new);
    Long[] baseNotesByName = Arrays.stream(baseNotes).filter(s -> s != "none").map(s -> Long.parseLong(s.replaceAll("\"", "")))
        .toArray(Long[]::new);

    List<String> topNote = noteRepository.findByIdIn(topNotesByName)
        .stream()
        .map(Note::getKoName)
        .collect(Collectors.toList());
    List<String> middleNote = noteRepository.findByIdIn(middleNotesByName)
        .stream()
        .map(Note::getKoName)
        .collect(Collectors.toList());
    List<String> baseNote = noteRepository.findByIdIn(baseNotesByName)
        .stream()
        .map(Note::getKoName)
        .collect(Collectors.toList());


    // 조회수 (update)
    PerfumeDetail perfumeDetail = perfumeDetailRepository.findByPerfume_Id(perfumeId);
    Long findPerfumeViewCnt = perfumeDetail.updateViewCount();

    // 향수의 리뷰 리스트를 Dto로 반환
    List<FindAllReviewByPerfumeId> findReviewList = findPerfume.getReviewList().stream()
        .map(r -> new FindAllReviewByPerfumeId(r.getSeason(), r.getPreference(), r.getLongevity(), r.getSillage(), r.getMember().getMemberProfile().getNickname()))
        .collect(Collectors.toList());
//    List<Review> reviewList = findPerfume.getReviewList();
//
//    List<FindAllReviewImageByPerfumeId> findReviewImageList = findPerfume.getReviewList().stream()
//            .filter(r -> r.getReviewImage() != null)                                         // null아닌거만 가져옴
//            .sorted(Comparator.comparing((Review r) -> r.getReviewImage().getLikeCnt()).reversed())     // 인기순으로 정렬(좋아요순)
//            .map(r -> new FindAllReviewImageByPerfumeId(r.getReviewImage().getImage(),
//                    r.getReviewImage().getLikeCnt()))
//            .collect(Collectors.toList());

    // todo: 페이징처리
    List<FindAllReviewImageByPerfumeId> findReviewImageList = findPerfume.getReviewList().stream()
        .filter(r -> Optional.ofNullable(r.getReviewImage())
                .map(ReviewImage::getImage)
                .isPresent())                                         // null아닌거만 가져옴
        .sorted(Comparator.comparing((Review r) -> r.getReviewImage().getLikeCnt()).reversed())     // 인기순으로 정렬(좋아요순)
        .map(r -> new FindAllReviewImageByPerfumeId(r.getReviewImage().getImage(),
            r.getReviewImage().getLikeCnt()))
        .collect(Collectors.toList());

    FindPerfumeResponse findPerfumeResponse = FindPerfumeResponse.create(findPerfume, topNote, middleNote, baseNote, findPerfumeViewCnt, findReviewList, findReviewImageList);
    return findPerfumeResponse;
  }


  /**
   * 인기 향수 목록 조회 (조회수)
   * limit - 12개
   */
  @Override
  public List<FindAllPopularPerfumeResponse> findTop12ByOrderByViewCntDesc() {
    List<PerfumeDetail> popularList = perfumeDetailRepository.findTop12ByOrderByViewCntDesc();
    // DTO로 변환
    List<FindAllPopularPerfumeResponse> collect = popularList.stream()
        .map(p -> new FindAllPopularPerfumeResponse(p.getPerfume().getId(), p.getPerfume().getKoName(), p.getPerfume().getKoBrand(), p.getPerfume().getImgURL()))
        .collect(Collectors.toList());
    return collect;
  }

  /**
   * 향수 리뷰 이미지 상세 조회
   */
  @Override
  public FindReviewImageResponse findReviewImage(String token, Long reviewImageId) {
    Long memberId = jwtService.getUserIdFromToken(token);

    // 멤버 조회
    Optional<Member> member = memberRepository.findById(memberId); // accessToken으로 교체
    Member findMember = member.orElseThrow(() -> new IllegalStateException("존재하는 회원이 없습니다."));

    // 리뷰 이미지 조회
    Optional<ReviewImage> reviewImage = reviewImageRepository.findById(reviewImageId);
    ReviewImage findReviewImage = reviewImage.orElseThrow(() -> new IllegalStateException("향수 리뷰 이미지가 없습니다."));

    // 좋아요 여부
    String likeImageList = findMember.getMemberLike().getLikeImage();
    Boolean isLike = false;
    if (likeImageList.contains(reviewImageId.toString())) {
      // 좋아요
      isLike = true;
    } else {
      // 좋아요 안한상태
      isLike = false;
    }

    FindReviewImageResponse findReviewImageResponse = FindReviewImageResponse.create(findReviewImage, isLike);
    return findReviewImageResponse;
  }

  /**
   * 향수 리뷰 이미지 좋아요 누르기
   */
  @Transactional
  @Override
  public IsLikeResponse isLike(String token, IsLikeRequest request) {
    Long memberId = jwtService.getUserIdFromToken(token);

    // 리뷰 이미지
    Optional<ReviewImage> reviewImage = reviewImageRepository.findById(request.getReviewImageId());
    ReviewImage findReviewImage = reviewImage.orElseThrow(() -> new IllegalStateException("존재하지 않는 이미지입니다."));

    // 멤버 조회
    Optional<Member> member = memberRepository.findById(memberId);
    Member findMember = member.orElseThrow(() -> new IllegalStateException("존재하는 회원이 없습니다."));

    // 멤버 id로 member_like 테이블 조회
    Optional<MemberLike> memberLike_id = Optional.ofNullable(memberLikeRepository.findByMember_Id(memberId));
    MemberLike newMemberLike = memberLike_id.orElse(MemberLike.builder()                                         // null일 때
        .likeImage("none, ")
        .time(LocalDateTime.now())
        .member(findMember)
        .build());
    memberLikeRepository.save(newMemberLike);

    // 좋아요 리스트
    String likeImageList = newMemberLike.getLikeImage();
    // 좋아요 수
    Long likeCnt = findReviewImage.getLikeCnt();

    if (request.getIsLike()) {
      newMemberLike.setLikeImage(likeImageList + request.getReviewImageId().toString() + ", ");        // 좋아요 목록에 추가
      likeCnt = findReviewImage.addLikeCnt();                                                          // 좋아요 카운트 +1

    } else {
      newMemberLike.setLikeImage(likeImageList.replaceAll(request.getReviewImageId().toString(), ""));
      likeCnt = findReviewImage.removeLikeCnt();
    }

    // DTO
    IsLikeResponse isLikeResponse = IsLikeResponse.create(newMemberLike.getLikeImage() , request.getIsLike(), likeCnt);
    return isLikeResponse;
  }

  /*
   * 향수 검색
   */
  @Override
  public List<SearchPerfumeResponse> searchPerfume(String perfume) {
    List<Perfume> perfumes = perfumeRepository.search(perfume);
    ModelMapper mapper = new ModelMapper();
    if(perfumes.size() == 0){
      perfumes = perfumeRepository.findBySearchTermDefault(perfume);
    }
    List<SearchPerfumeResponse> searchPerfumeResponse = perfumes.stream()
            .map(i -> mapper.map(i, SearchPerfumeResponse.class))
            .collect(Collectors.toList());
    return searchPerfumeResponse;
  }

  /*
   * 연령 향수 조회
   */

  @Override
  public List<SearchPerfumeByAgeResponse> searchPerfumeByAge() {
    Long[] age = {932L, 2154L, 1494L, 979L, 2194L, 954L, 2143L, 2197L, 1108L, 976L, 1258L, 1124L};
    List<Perfume> perfumes = perfumeRepository.findByIdIn(Arrays.stream(age).collect(Collectors.toList()));

    List<SearchPerfumeByAgeResponse> searchPerfumeByAgeResponse = new ArrayList<>();
    for(Perfume perfume : perfumes){
          searchPerfumeByAgeResponse.add(
                  SearchPerfumeByAgeResponse.builder()
                  .id(perfume.getId())
                  .name(perfume.getKoName())
                  .brand(perfume.getKoBrand())
                  .imgURL(perfume.getImgURL())
                  .build()
      );
    }
    return searchPerfumeByAgeResponse;
  }

}