package com.project.mything.auth.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.mything.member.repository.RefreshTokenRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String accessHeader;

//    @Value("${jwt.refresh.header}")
//    private String refreshHeader;

    /**
     * JWT의 Subject와 Claim으로 email 사용 -> 클레임의 name을 "id"으로 설정
     * JWT의 헤더에 들어오는 값 : 'Authorization(Key) = Bearer {토큰} (Value)' 형식
     */
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String ID_CLAIM = "kakaoId";
    private static final String BEARER = "Bearer ";
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * AccessToken 생성 메소드
     */
    public String createAccessToken(String kakaoId) {
        Date now = new Date();
        log.info("지금시간 {}", now);
        return JWT.create() // JWT 토큰을 생성하는 빌더 반환
                .withSubject(ACCESS_TOKEN_SUBJECT) // JWT의 Subject 지정 -> AccessToken이므로 AccessToken
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod)) // 토큰 만료 시간 설정

                .withClaim(ID_CLAIM, kakaoId)
                .sign(Algorithm.HMAC512(secretKey)); // HMAC512 알고리즘 사용, application-jwt.yml에서 지정한 secret 키로 암호화
    }

    /**
     * RefreshToken 생성
     * RefreshToken은 Claim에 id도 넣지 않으므로 withClaim() X
     */
    public String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(secretKey));
    }

    /**
     * AccessToken에서 id 추출
     */

    public Long getUserIdFromToken(String token) {
        Optional<String> newToken = Optional.ofNullable(token)
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));

        log.info("내 토큰 = {}", newToken.get());

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(secretKey))
                .build()
                .verify(newToken.get());

        String kakaoIdStr = decodedJWT.getClaim(ID_CLAIM).asString();  // ID_CLAIM 이름의 클레임에서 문자열 값을 추출하여 변수에 저장

        return Long.parseLong(kakaoIdStr);
    }


    public Long getUserId(String token) {
//        Optional<String> newToken = Optional.ofNullable(token)
//                .filter(refreshToken -> refreshToken.startsWith(BEARER))
//                .map(refreshToken -> refreshToken.replace(BEARER, ""));

//        log.info("내 토큰 = {}", newToken.get());

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(secretKey))
                .build()
                .verify(token);

        String kakaoIdStr = decodedJWT.getClaim(ID_CLAIM).asString();  // ID_CLAIM 이름의 클레임에서 문자열 값을 추출하여 변수에 저장

        return Long.parseLong(kakaoIdStr);
    }

    /**
     * AccessToken 헤더에 실어서 보내기
     */
    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader(accessHeader, accessToken);
//        log.info("response : {}", response.getHeader(accessHeader));
        log.info("Access Token 헤더 설정 완료");
        log.info("발급된 Access Token : {}", accessToken);
    }

//    /**
//     * AccessToken + RefreshToken 헤더에 실어서 보내기
//     */
//    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
//        response.setStatus(HttpServletResponse.SC_OK);
//
//        setAccessTokenHeader(response, accessToken);
//        setRefreshTokenHeader(response, refreshToken);
//        log.info("Access Token, Refresh Token 헤더 설정 완료");
//        log.info("Access Token : " +  accessToken + " " + "Refresh Token : " + refreshToken);
//    }


    /**
     * 헤더에서 RefreshToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
//    public Optional<String> extractRefreshToken(HttpServletRequest request) {
//        return Optional.ofNullable(request.getHeader(refreshHeader))
//                .filter(refreshToken -> refreshToken.startsWith(BEARER))
//                .map(refreshToken -> refreshToken.replace(BEARER, ""));
//    }

    /**
     * 헤더에서 AccessToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(accessToken -> accessToken.startsWith(BEARER))
                .map(accessToken -> accessToken.replace(BEARER, ""));
//        return Optional.ofNullable(request.getHeader(accessHeader))
//                .filter(refreshToken -> refreshToken.startsWith(BEARER))
//                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /**
     * AccessToken에서 id 추출
     * 추출 전에 JWT.require()로 검증기 생성
     * verify로 AceessToken 검증 후
     * 유효하다면 getClaim()으로 id 추출
     * 유효하지 않다면 빈 Optional 객체 반환
     */
    public Optional<String> extractId(String accessToken) {
        try {
            // 토큰 유효성 검사하는 데에 사용할 알고리즘이 있는 JWT verifier builder 반환
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build() // 반환된 빌더로 JWT verifier 생성
                    .verify(accessToken) // accessToken을 검증하고 유효하지 않다면 예외 발생
                    .getClaim(ID_CLAIM) // claim(id) 가져오기
                    .asString());
        } catch (Exception e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    /**
     * AccessToken 헤더 설정
     */
    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(accessHeader, accessToken);
    }

//    /**
//     * RefreshToken 헤더 설정
//     */
//    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
//        response.setHeader(refreshHeader, refreshToken);
//    }

    /**
     * RefreshToken DB 저장(업데이트)
     */
    public void updateRefreshToken(String kakaoId, String refreshToken) {
        refreshTokenRepository.findByMemberId(Long.parseLong(kakaoId))
                .ifPresentOrElse(
                        token -> token.updateRefreshToken(refreshToken),
                        () -> new Exception("일치하는 회원이 없습니다.")
                );
    }

    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            return false;
        }
    }


//    public boolean isTokenFired(String token) {
//        try {
//            DecodedJWT jwt = JWT.decode(token);
//            Date expiration = jwt.getExpiresAt();
//            if (expiration.after(new Date())) {
//                log.error("토큰이 만료되었습니다.");
//                return false;
//            }
//            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
//            return true;
//        } catch (Exception e) {
//            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
//            return false;
//        }
//    }


    /**
     * AccessToken에서 id 추출
     * 추출 전에 JWT.require()로 검증기 생성
     * verify로 AceessToken 검증 후
     * 유효하다면 getClaim()으로 id 추출
     * 유효하지 않다면 빈 Optional 객체 반환
     */
//    public Optional<String> getId(String accessToken) {
//        try {
//            // accessToken에서 claim(id) 값을 가져오기
//            DecodedJWT jwt = JWT.decode(accessToken);
//            return Optional.ofNullable(jwt.getClaim(ID_CLAIM).asString());
//        } catch (JWTDecodeException e) {
//            log.error("액세스 토큰 디코딩에 실패했습니다.");
//            return Optional.empty();
//        } catch (Exception e) {
//            log.error("액세스 토큰이 유효하지 않습니다.");
//            return Optional.empty();
//        }
//    }
}
