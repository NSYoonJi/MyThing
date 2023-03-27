package com.project.mything.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticaitonFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = request.getHeader("Authorization");

            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            String userId = claims.getSubject();
            if (userId != null) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {
            logger.error("Failed to set user authentication in security context: " + e);
        }

        //        String token = request.getHeader("Authorization");
//        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
//            Authentication authentication = jwtTokenProvider.getAuthentication(token);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        String refreshToken = request.getHeader(REFRESH_TOKEN_HEADER);
//        if (StringUtils.hasText(refreshToken) && jwtTokenProvider.validateToken(refreshToken)) {
//            String username = jwtTokenProvider.getUsername(refreshToken);
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            String newToken = jwtTokenProvider.createToken(authentication);
//            response.addHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + newToken);
//
//            String newRefreshToken = jwtTokenProvider.createRefreshToken(authentication);
//            response.addHeader(REFRESH_TOKEN_HEADER, newRefreshToken);
//        }

        filterChain.doFilter(request, response);
    }




}
