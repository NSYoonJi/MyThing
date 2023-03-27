package com.project.mything.auth.jwt;

import com.project.mything.auth.Repository.MemberRepository;
import com.project.mything.auth.Repository.RefreshTokenRepository;
import com.project.mything.member.entity.Member;
import com.project.mything.member.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        System.out.println("확인황인~~~~~~");
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        Map<String, Object> attributes =oAuth2User.getAttributes();
        String id = String.valueOf(attributes.get("id"));
        // access, refresh 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(id);
        String refreshToken = jwtTokenProvider.createRefreshToken(id);
        System.out.println("jwt토큰: "+ accessToken);
        // refreshtoken db 저장
        Long memberId = Long.parseLong(id);
        Optional<Member> findMember =  memberRepository.findById(memberId);
        Member getMember = findMember.get();
        RefreshToken refreshToken1 = RefreshToken.builder().
                refreshToken(refreshToken).
                member(getMember).
                build();
        refreshTokenRepository.save(refreshToken1);

        response.setContentType("application/json;charset=UTF-8");
        response.addHeader("Authorization", "Bearer "+ accessToken);
        PrintWriter out = response.getWriter();
        out.print("{\"success\":true}");
        out.flush();
        out.close();

    }
}
