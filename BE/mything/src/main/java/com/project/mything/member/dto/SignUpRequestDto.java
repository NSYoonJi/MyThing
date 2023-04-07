package com.project.mything.member.dto;

import lombok.Data;

@Data
public class SignUpRequestDto {
    private String nickname;
    private String gender;
    private String date;
    private String prefer_insence;
    private String hate_insence;
    private Long id;
    private String name;
    private String brand;
    private String imgURL;
}
