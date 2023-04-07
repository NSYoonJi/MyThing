package com.project.mything.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("SurveyPerfumeRequestDto")
public class SurveyPerfumeRequestDto {
    private Long id;
    private String name;
    private String brand;
    private String imgURL;
}
