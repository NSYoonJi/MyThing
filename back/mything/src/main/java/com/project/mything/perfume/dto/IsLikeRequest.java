package com.project.mything.perfume.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * packageName    : com.project.mything.perfume.dto fileName       : IsLikeRequest author         :
 * hagnoykmik date           : 2023-03-28 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-03-28        hagnoykmik       최초 생성
 */
@AllArgsConstructor
@Data
@Builder
public class IsLikeRequest {

  private Long reviewImageId;
  private Boolean isLike;

}
