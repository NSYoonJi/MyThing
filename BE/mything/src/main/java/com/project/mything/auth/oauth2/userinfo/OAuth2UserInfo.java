package com.project.mything.auth.oauth2.userinfo;

import java.util.Map;

public class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

//    public String getImageUrl() {
//        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
//        Map<String, Object> profile = (Map<String, Object>) account.get("profile");
//
//        if (account == null || profile == null) {
//            return null;
//        }
//        return (String) profile.get("thumbnail_image_url");
//    }
}
