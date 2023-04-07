package com.project.mything.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class InfoResponseDto {
    private List<String> preference;
    private String nickname;
    private String imagePath;

    private String gender;
    private String birth;


}
