package com.project.mything.survey.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendPerfumesResponse {
    private Long id;
    private String name;
    private String brand;
    private String imgURL;
    private String status;
}
