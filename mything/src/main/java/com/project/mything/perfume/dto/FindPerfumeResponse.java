package com.project.mything.perfume.dto;

import com.project.mything.perfume.entity.Perfume;
import com.project.mything.perfume.entity.PerfumeDetail;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.project.mything.perfume.dto fileName       : FindPerfumeResponse author
 *    : SSAFY date           : 2023-03-24 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-03-24        SSAFY       최초 생성
 */
@AllArgsConstructor
@Data
@Getter
@Setter
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
  
  public static FindPerfumeResponse create(Perfume perfume) {
    FindPerfumeResponse foundPerfume = FindPerfumeResponse.builder()
        .name(perfume.getName())
        .brand(perfume.getBrand())
        .info(perfume.getInfo())
        .topNote(perfume.getTopNote())
        .middleNote(perfume.getMiddleNote())
        .baseNote(perfume.getBaseNote())
        /////////////////////////////////
        .love(perfume.getPerfumeDetail().getLove())
        .ok(perfume.getPerfumeDetail().getOk())
        .hate(perfume.getPerfumeDetail().getHate())
        .spring(perfume.getPerfumeDetail().getSpring())
        .summer(perfume.getPerfumeDetail().getSummer())
        .fall(perfume.getPerfumeDetail().getFall())
        .winter(perfume.getPerfumeDetail().getWinter())
        // todo: 조회수고쳐야함
        .viewCnt(perfume.getPerfumeDetail().getViewCnt() + 1)
        .build();
    return foundPerfume;
  }
}
