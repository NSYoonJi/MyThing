package com.project.mything.perfume.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SearchPerfumeByAgeResponse {
    private Long id;
    private String name;
    private String brand;
    private String imgURL;
}
