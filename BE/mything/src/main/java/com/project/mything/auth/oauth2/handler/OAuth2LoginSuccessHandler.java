package com.project.mything.auth.oauth2.handler;

import com.project.mything.auth.jwt.service.JwtService;
import com.project.mything.auth.oauth2.CustomOAuth2User;
import com.project.mything.member.entity.Member;
import com.project.mything.member.entity.RefreshToken;
import com.project.mything.member.entity.Role;
import com.project.mything.member.repository.MemberRepository;
import com.project.mything.member.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
//@Transactional
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            // User의 Role이 GUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
            if (oAuth2User.getRole() == Role.GUEST) {
                String accessToken = jwtService.createAccessToken(oAuth2User.getTestId());
                response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);

                String redirectUrl = "http://makeyourpreference.com/preference?code=" + accessToken;
                response.sendRedirect(redirectUrl);
            } else {
                loginSuccess(response, oAuth2User); // 로그인에 성공한 경우 access, refresh 토큰 생성
            }
        } catch (Exception e) {
            throw e;
        }

    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String memberId = oAuth2User.getTestId();
        Optional<Member> findMember = memberRepository.findById(Long.parseLong(memberId));
        Member member = findMember.orElseThrow(() -> new IllegalStateException("유저가 존재하지 않음"));
        String accessToken = jwtService.createAccessToken(memberId);
        String refreshToken = jwtService.createRefreshToken();

        RefreshToken pastToken = refreshTokenRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new IllegalStateException("리프레쉬 토큰 없음"));

        pastToken.updateRefreshToken(refreshToken);

        jwtService.sendAccessToken(response, accessToken);
        jwtService.updateRefreshToken(oAuth2User.getTestId(), refreshToken);
        String redirectUrl = "http://makeyourpreference.com/mainpage?code=" + accessToken;
        response.sendRedirect(redirectUrl);
    }
}
