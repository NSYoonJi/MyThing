package com.project.mything.perfume.dto;

import com.project.mything.perfume.entity.Perfume;
import com.project.mything.perfume.entity.PerfumeDetail;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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

@AllArgsConstructor
@Data
@Builder
public class FindPerfumeResponse {
  // perfume
  private String name;
  private String brand;
  private String info;
  private List<String> topNote;
  private List<String> middleNote;
  private List<String> baseNote;
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
  // review_list
  private List<FindAllReviewByPerfumeId> reviewList;
  // review_image_list
  private List<FindAllReviewImageByPerfumeId> reviewImageList;
  
  public static FindPerfumeResponse create(Perfume perfume, List<String> topNote, List<String> middleNote, List<String> baseNote, Long viewCnt, List<FindAllReviewByPerfumeId> review, List<FindAllReviewImageByPerfumeId> reviewImageList) {
    FindPerfumeResponse foundPerfume = FindPerfumeResponse.builder()
        .name(perfume.getKoName())
        .brand(perfume.getKoBrand())
        .info(perfume.getKoDescription())
        .topNote(topNote)
        .middleNote(middleNote)
        .baseNote(baseNote)
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
