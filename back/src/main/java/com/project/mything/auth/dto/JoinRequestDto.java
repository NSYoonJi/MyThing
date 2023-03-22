package com.project.mything.auth.dto;

import lombok.Data;

import java.util.List;

@Data
public class JoinRequestDto {
    private String nickname;
    private String imagePath;

    private String gender;

    private String date;

    private List<String> prefer_insence;
    private List<String> hate_insence;



}
