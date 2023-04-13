package com.project.mything.member.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("JoinRequestDto")
public class JoinRequestDto {
    private String nickname;
    private String gender;
    private String date;
    private String prefer_insence;
    private String hate_insence;

}
