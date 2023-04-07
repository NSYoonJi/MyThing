package com.project.mything.perfume.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindNotesByCategoryResponse {
    private Long noteId;
    private String image;
    private String koName;
    private Long categoryId;
}
