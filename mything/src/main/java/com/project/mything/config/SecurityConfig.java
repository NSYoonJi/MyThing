package com.project.mything.config;

import com.project.mything.auth.Service.UserOAuthService;
import com.project.mything.auth.jwt.JwtAuthenticaitonFilter;
import com.project.mything.auth.jwt.OAuth2AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserOAuthService userOAuth2Service;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Autowired
    JwtAuthenticaitonFilter jwtAuthenticaitonFilter;
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.httpBasic().disable()
                .csrf().disable()
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //.and()
                .formLogin().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/auth/**","/survey/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().deleteCookies("JSESSIONID")
                .and()
                .oauth2Login().userInfoEndpoint().userService(userOAuth2Service)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler);

            http.addFilterBefore(jwtAuthenticaitonFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
