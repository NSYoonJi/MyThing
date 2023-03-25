package com.project.mything.perfume.dto;

import com.project.mything.perfume.entity.Perfume;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
  private String topNote;
  private String middleNote;
  private String baseNote;
  // perfume_detail
  private Long viewCnt;
  private Long love;
  private Long ok;
  private Long hate;
  private Long spring;
  private Long summer;
  private Long fall;
  private Long winter;
  
  public static FindPerfumeResponse create(Perfume perfume, Long viewCnt) {
    FindPerfumeResponse foundPerfume = FindPerfumeResponse.builder()
        .name(perfume.getName())
        .brand(perfume.getBrand())
        .info(perfume.getInfo())
        .topNote(perfume.getTopNote())
        .middleNote(perfume.getMiddleNote())
        .baseNote(perfume.getBaseNote())
        .love(perfume.getPerfumeDetail().getLove())
        .ok(perfume.getPerfumeDetail().getOk())
        .hate(perfume.getPerfumeDetail().getHate())
        .spring(perfume.getPerfumeDetail().getSpring())
        .summer(perfume.getPerfumeDetail().getSummer())
        .fall(perfume.getPerfumeDetail().getFall())
        .winter(perfume.getPerfumeDetail().getWinter())
        .viewCnt(viewCnt)
        .build();
    return foundPerfume;
  }
}
