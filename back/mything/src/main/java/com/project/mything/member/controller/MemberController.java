package com.project.mything.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.mything.auth.jwt.service.JwtService;
import com.project.mything.member.dto.*;
import com.project.mything.member.service.MemberService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final JwtService jwtService;

    // 회원 정보 조회
    @GetMapping(value = "/members")
    public ResponseEntity<?> memberInfo(@RequestHeader("Authorization") String token) {
        InfoResponseDto infoResponseDto = memberService.findMember(token);

        return new ResponseEntity<>(infoResponseDto, HttpStatus.OK);
    }

    // 회원정보 수정
    // TODO PatchMapping?!>!>!>
    @PatchMapping(value = "/members")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String token, @RequestBody InfoUpdateRequestDto infoUpdateRequestDto) {
        Long mid = memberService.updateMember(token, infoUpdateRequestDto);
        return new ResponseEntity<>(mid, HttpStatus.OK);
    }

    // 선호향, 비선호향 수정
    @PatchMapping(value = "/preference")
    public ResponseEntity<?> preference(@RequestHeader("Authorization") String token, @RequestBody PreferenceRequestDto preferenceRequestDto) {
        Long mid = memberService.updatePreference(token, preferenceRequestDto);
        return new ResponseEntity<>(mid, HttpStatus.OK);
    }

//    @PostMapping("/join")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "joinRequestDto", value = "회원 가입 요청 정보", dataType = "com.example.dto.JoinRequestDto", required = true),
//            @ApiImplicitParam(name = "surveyPerfumeRequestDto", value = "설문조사 요청 정보", dataType = "com.example.dto.SurveyPerfumeRequestDto", required = true)
//    })
//    public ResponseEntity<?> signUp(@RequestHeader("Authorization") String token, ObjectNode objectNode) throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        JoinRequestDto joinRequest = mapper.treeToValue(objectNode.get("JoinRequest"), JoinRequestDto.class);
//        SurveyPerfumeRequestDto surveyPerfumeRequest = mapper.treeToValue(objectNode.get("SurveyPerfumeRequest"), SurveyPerfumeRequestDto.class);
//
//        log.info("SurveyPerfumeRequest : {}", surveyPerfumeRequest.toString());
//
//        String result = memberService.join(token, joinRequest, surveyPerfumeRequest);
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

    @PostMapping("/join")
    public ResponseEntity<?> signUp(@RequestHeader("Authorization") String token, @RequestPart SignUpRequestDto signUpRequestDto, @RequestPart(required = false) MultipartFile image) throws Exception {
//        JoinRequestDto joinRequest = signUpRequestDto.getJoinRequestDto();
//        SurveyPerfumeRequestDto surveyPerfumeRequest = signUpRequestDto.getSurveyPerfumeRequestDto();

        log.info("SurveyPerfumeRequest : {}", signUpRequestDto.toString());

        String result = memberService.join(token, signUpRequestDto, image);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
