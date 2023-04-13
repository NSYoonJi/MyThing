package com.project.mything.perfume.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PreferNotesRequest {

    private String[] topNotes;
    private String[] baseNotes;
    private String[] middleNotes;

}
