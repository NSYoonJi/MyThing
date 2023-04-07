package com.project.mything.member.controller;

import com.project.mything.auth.jwt.service.JwtService;
import com.project.mything.member.dto.InfoResponseDto;
import com.project.mything.member.dto.InfoUpdateRequestDto;
import com.project.mything.member.dto.PreferenceRequestDto;
import com.project.mything.member.dto.SignUpRequestDto;
import com.project.mything.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public ResponseEntity<?> update(@RequestHeader("Authorization") String token, @RequestPart InfoUpdateRequestDto infoUpdateRequestDto, @RequestPart(required = false) MultipartFile image) throws IOException {
//    public ResponseEntity<?> update(@RequestHeader("Authorization") String token, @RequestBody InfoUpdateRequestDto infoUpdateRequestDto) {
        Long mid = memberService.updateMember(token, infoUpdateRequestDto, image);
        return new ResponseEntity<>(mid, HttpStatus.OK);
    }

    // 선호향, 비선호향 수정
    @PatchMapping(value = "/preference")
    public ResponseEntity<?> preference(@RequestHeader("Authorization") String token, @RequestBody PreferenceRequestDto preferenceRequestDto) {
        Long mid = memberService.updatePreference(token, preferenceRequestDto);
        return new ResponseEntity<>(mid, HttpStatus.OK);
    }

    /*
       추가정보등록
       처음 소셜 로그인 시 추가정보 등록
       이 때 설문조사 결과가 있으면 같이 등록하고 없을 경우
       설문조사 페이지로 이동
     */
    @PostMapping("/join")
    public ResponseEntity<?> signUp(@RequestHeader("Authorization") String token, @RequestPart SignUpRequestDto signUpRequestDto, @RequestPart(required = false) MultipartFile image) throws Exception {
        String result = memberService.join(token, signUpRequestDto, image);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
