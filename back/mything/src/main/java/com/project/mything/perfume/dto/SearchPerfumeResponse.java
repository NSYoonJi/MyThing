package com.project.mything.perfume.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchPerfumeResponse {
    private Long id;
    private String koName;
    private String koBrand;
    private String imgURL;
}