package com.project.mything.perfume.dto;

import com.project.mything.perfume.entity.Perfume;
import com.project.mything.perfume.entity.PerfumeDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * packageName    : com.project.mything.perfume.dto fileName       : FindAllResponse author
 * : SSAFY date           : 2023-03-25 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-03-25        SSAFY       최초 생성
 */
@AllArgsConstructor
@Data
@Builder
public class FindAllPopularPerfumeResponse {
  private Long id;
  private String name;
  private String brand;
  private String imgURL;

  public static FindAllPopularPerfumeResponse create(PerfumeDetail perfume) {
    FindAllPopularPerfumeResponse findAllPopularPerfumeResponse = FindAllPopularPerfumeResponse.builder()
        .id(perfume.getPerfume().getId())
        .name(perfume.getPerfume().getName())
        .brand(perfume.getPerfume().getBrand())
        .imgURL(perfume.getPerfume().getImgURL())
        .build();
    return findAllPopularPerfumeResponse;

  }
}
