package com.project.mything.auth.Service;

import com.project.mything.auth.PrincipleDetails;
import com.project.mything.auth.Repository.MemberRepository;
import com.project.mything.member.entity.Gender;
import com.project.mything.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserOAuthService extends DefaultOAuth2UserService {
    @Autowired
    private MemberRepository memberRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("access_token: "+ userRequest.getAccessToken().getTokenValue());
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes =oAuth2User.getAttributes();

        System.out.println("attributes id : "+ attributes.get("id"));
        Object o = attributes.get("id");
        Long id = Long.parseLong(String.valueOf(o));
        Member member = new Member();
        boolean b = memberRepository.existsById(id);
        if(!b){
            member = Member.builder()
                    .id(id)
                    .year("1997")
                    .gender(Gender.FEMALE)
                    .build();
            memberRepository.save(member);
        }else{
            System.out.println("이미 회원가입을 한 적 있는 유저입니다");
        }

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_MEMBER")), attributes, "id");
        //return new PrincipleDetails(member, attributes);
    }
}
