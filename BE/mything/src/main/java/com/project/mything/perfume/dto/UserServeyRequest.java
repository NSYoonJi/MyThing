package com.project.mything.perfume.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserServeyRequest {
//    private Long id;
    private String[] topNotes;
    private String[] baseNotes;
    private String[] middleNotes;
}
