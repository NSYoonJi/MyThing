package com.project.mything.config;

import com.project.mything.auth.Service.UserOAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserOAuthService userOAuth2Service;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/auth/**","/survey/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login().defaultSuccessUrl("/").userInfoEndpoint().userService(userOAuth2Service);



//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않겠다는 뜻
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS).permitAll()
//                .antMatchers("auth/**","/survey/**","/login/oauth2/code/kakao","/swagger*/**","/kauth.kako.com/**","/login/**").permitAll()
//                .anyRequest().authenticated().and() // 해당 요청을 인증된 사용자만 사용 가능
//                .oauth2Login() // oauth2Login() -> userInfoEndpoint().userService -> successHandler 순으로 실행!
//                .defaultSuccessUrl("/")
//                //.successHandler(oAuth2LoginSuccessHandler)
//                .userInfoEndpoint()
//                .userService(userOAuth2Service);
//    //                .and()
////                .addFilterBefore();
        return http.build();
    }
}
