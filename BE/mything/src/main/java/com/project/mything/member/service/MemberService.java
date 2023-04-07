package com.project.mything.member.service;

import com.project.mything.member.dto.InfoResponseDto;
import com.project.mything.member.dto.InfoUpdateRequestDto;
import com.project.mything.member.dto.PreferenceRequestDto;
import com.project.mything.member.dto.SignUpRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MemberService {
    public String join(String token, SignUpRequestDto signUpRequestDto, MultipartFile image) throws Exception;
    public InfoResponseDto findMember(String accessToken);
    public Long updateMember(String token, InfoUpdateRequestDto infoUpdateRequestDto, MultipartFile img) throws IOException;
    Long updatePreference(String token, PreferenceRequestDto preferenceRequestDto);
    Boolean findNickname (String nickname);
}
