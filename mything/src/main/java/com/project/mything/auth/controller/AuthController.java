package com.project.mything.auth.controller;


import com.project.mything.auth.Service.KakaoAuthService;
import com.project.mything.auth.dto.JoinRequestDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final KakaoAuthService kakaoAuthService;



    /*@GetMapping(value = "/login/oauth2/code/kakao")
    public @ResponseBody String kakaoCallback(@RequestParam String code, HttpServletResponse response) throws IOException, ParseException {
        kakaoAuthService.kakaoLogin(code, response);



        return "response"+ code;
    }

     */
    @GetMapping("/")
    public String index(){
        return "메인";
    }
    // 회원가입시 추가 정보 입력
    @GetMapping(value = "/join")
    public ResponseEntity<?> join(@RequestHeader("Id")String id, @RequestBody JoinRequestDto joinRequestDto) throws Exception {

        kakaoAuthService.join(joinRequestDto, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
