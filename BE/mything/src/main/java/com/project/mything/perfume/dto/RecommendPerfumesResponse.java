package com.project.mything.perfume.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendPerfumesResponse implements Serializable{
    private Long id;
    private String name;
    private String brand;
    private String imgURL;
    private String status;
}
