package com.project.mything.auth.Service;

import com.project.mything.auth.dto.JoinRequestDto;
import com.project.mything.member.entity.Member;
import com.project.mything.member.entity.MemberProfile;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.http.*;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {
    // 추가정보 - user 로 옮길까 고민
    public void join(JoinRequestDto joinRequestDto) {
        //Member member = new Member();
        //MemberProfile memberProfile = new MemberProfile();

    }

    public void kakaoLogin(String code, HttpServletResponse response) throws ParseException {
        // 1. 인가 코드로 엑세스 토큰 요청
        String accessToken = getAccessToken(code);
        // 2. 엑세스 토큰으로 카카오 사용자 정보 가져오기
        Long kakaoId = getKakaoUserInfo(accessToken);
        //3. 회원가입
       // Member member = registerKakaoMember(kakaoId);
    }

    private Member registerKakaoMember(Long kakaoId) {

        return null;
    }

    private Long getKakaoUserInfo(String accessToken) throws ParseException {
        // HttpBody 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        // HTTP 요청보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                kakaoUserInfoRequest,
                String.class
        );

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
            String kakaoId = String.valueOf(jsonObject.get("id"));
            System.out.println("kakaoId String" + kakaoId);
            long kakaoi = Long.parseLong(kakaoId);
            System.out.printf("kakaoId Long%d%n", kakaoi);
            return kakaoi;


    }

    private String getAccessToken(String code){

        // POST 방식으로 key =value 데이터를 요청 (카카오쪽으로)
        RestTemplate rt = new RestTemplate();
        // HttpBody 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "9eec0e493dbf3db1f50e9930111e3715");
        params.add("redirect_uri", "http://localhost:8080/login/oauth2/code/kakao");
        params.add("code", code);
        // HttpHeader 와 HttpBody 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
        // Http 요청하기 - Post 방식으로
        System.out.println(kakaoTokenRequest.getHeaders());
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
            String accessToken = (String) jsonObject.get("access_token");
            System.out.println("access_token: "+accessToken);
            System.out.println("refresh_token: " + jsonObject.get("refresh_token"));
            return accessToken;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }
}
