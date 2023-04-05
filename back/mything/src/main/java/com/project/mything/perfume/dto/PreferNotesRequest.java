package com.project.mything.perfume.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PreferNotesRequest {

    private String[] topNotes;
    private String[] baseNotes;
    private String[] middleNotes;

}
