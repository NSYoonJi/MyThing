package com.project.mything.perfume.dto;

import com.project.mything.perfume.entity.Perfume;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * packageName    : com.project.mything.perfume.dto fileName       : FindPerfumeResponse author
 *    : hagnoykmik date           : 2023-03-24 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-03-24        hagnoykmik       최초 생성
 */
@AllArgsConstructor
@Data
@Builder
public class FindPerfumeResponse {
  // perfume
  private String name;
  private String brand;
  private String info;
  private String notes;
  // todo: 수정필요
//  private String topNote;
//  private String middleNote;
//  private String baseNote;
  private String perfumeImgURL;
  // perfume_detail
  private Long viewCnt;
  private Long love;
  private Long ok;
  private Long hate;
  private Long spring;
  private Long summer;
  private Long fall;
  private Long winter;
  // review
  // todo:reviewId를 가져옴
  private List<FindAllReviewByPerfumeId> reviewList;
  // todo:review_image
  private List<FindAllReviewImageByPerfumeId> reviewImageList;
  
  public static FindPerfumeResponse create(Perfume perfume, Long viewCnt, List<FindAllReviewByPerfumeId> review, List<FindAllReviewImageByPerfumeId> reviewImageList) {
    FindPerfumeResponse foundPerfume = FindPerfumeResponse.builder()
        .name(perfume.getName())
        .brand(perfume.getBrand())
        .info(perfume.getInfo())
        .notes(perfume.getNotes())
//        .middleNote(perfume.getMiddleNote())
//        .baseNote(perfume.getBaseNote())
        .perfumeImgURL(perfume.getImgURL())
        //////
        .love(perfume.getPerfumeDetail().getLove())
        .ok(perfume.getPerfumeDetail().getOk())
        .hate(perfume.getPerfumeDetail().getHate())
        .spring(perfume.getPerfumeDetail().getSpring())
        .summer(perfume.getPerfumeDetail().getSummer())
        .fall(perfume.getPerfumeDetail().getFall())
        .winter(perfume.getPerfumeDetail().getWinter())
        .viewCnt(viewCnt)
        //////
        .reviewList(review)
        //////
        .reviewImageList(reviewImageList)
        .build();
    return foundPerfume;
  }
}
