package com.project.mything.member.service;

import com.project.mything.member.dto.*;
import com.project.mything.member.dto.JoinRequestDto;
import com.project.mything.member.dto.SurveyPerfumeRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
    public String join(String token, SignUpRequestDto signUpRequestDto, MultipartFile image) throws Exception;

    public InfoResponseDto findMember(String accessToken);

    public Long updateMember(String token, InfoUpdateRequestDto infoUpdateRequestDto);

    Long updatePreference(String token, PreferenceRequestDto preferenceRequestDto);
}
