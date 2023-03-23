package com.project.mything.auth.Service;

import com.project.mything.auth.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserOAuthService extends DefaultOAuth2UserService {
    @Autowired
    private MemberRepository memberRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("clientRegistration: "+userRequest.getClientRegistration()); // registrationId 로 어떤 OAuth 로 로그인했는지 확인가능
        System.out.println("access_token: "+ userRequest.getAccessToken().getTokenValue());
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return null;
    }
}
