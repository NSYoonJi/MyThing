package com.project.mything.perfume.dto;

import lombok.*;

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
