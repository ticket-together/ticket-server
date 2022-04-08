package com.tickettogether.global.config.security.oauth.dto;

import lombok.Getter;
import java.util.HashMap;
import java.util.Map;

@Getter
public class KakaoOAuthAttributes extends OAuthAttributes {

    public static OAuthAttributes of(String userNameAttribute, Map<String, Object> attributes) {
        Map<String, Object> attributeMap = (HashMap<String, Object>) attributes.get("kakao_account");
        if(!attributeMap.isEmpty()){
            Map<String, Object> profilesMap = (HashMap<String, Object>) attributeMap.get("profile");

            return OAuthAttributes.builder()
                    .attributes(attributes)
                    .nameKey(userNameAttribute)
                    .nickName((String) profilesMap.get("nickname"))
                    .email((String) attributeMap.get("email"))
                    .imgUrl((String) profilesMap.get("thumbnail_image_url"))
                    .build();
        }
        return null;
    }

    public static OAuthAttributes of(String registerId, String userNameAttribute, Map<String, Object> attributes) {
        Map<String, Object> attributeMap = (HashMap<String, Object>) attributes.get("kakao_account");
        if(!attributeMap.isEmpty()){
            Map<String, Object> profilesMap = (HashMap<String, Object>) attributeMap.get("profile");

            return OAuthAttributes.builder()
                    .registerId(registerId)
                    .attributes(attributes)
                    .nameKey(userNameAttribute)
                    .nickName((String) profilesMap.get("nickname"))
                    .email((String) attributeMap.get("email"))
                    .imgUrl((String) profilesMap.get("thumbnail_image_url"))
                    .build();
        }
        return null;
    }

    private static boolean validUserInfoPermission(Map<String, Object> map) {
        if (map.get("gender_needs_agreement").equals("true")) {
            return true;
        }
        return map.get("birthday_needs_agreement").equals("true");
    }

}
