package com.tickettogether.global.config.security.oauth.service;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.domain.Role;
import com.tickettogether.domain.member.repository.MemberRepository;
import com.tickettogether.global.config.security.oauth.dto.KakaoOAuthAttributes;
import com.tickettogether.global.config.security.oauth.dto.OAuthAttributes;
import com.tickettogether.global.config.security.oauth.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService defaultService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = defaultService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); //로그인 진행 중인 서비스를 구분하는 코드
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()   //로그인 진행 시 키가 되는 필드값(pk)
                .getUserNameAttributeName();

        OAuthAttributes attr = KakaoOAuthAttributes.of(userNameAttributeName, oAuth2User.getAttributes());
        Member member = saveOrUpdateMemberToDB(attr);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
                        attr.getAttributes(), userNameAttributeName);
    }

    public Member saveOrUpdateMemberToDB(OAuthAttributes attrs){
        //없다면 DB에 저장
        Member member = memberRepository.findByEmail(attrs.getEmail());
        if (Optional.ofNullable(member).isEmpty()){
            return memberRepository.save(attrs.toEntity());
        }
        //있다면 정보가 다를 경우에만 새로운 정보로 업데이트
        if (member.getName() != null & !member.getName().equals(attrs.getNickName()) ||
                member.getImgUrl() != null & !member.getImgUrl().equals(attrs.getImgUrl())){
            member.updateMemberProfile(attrs.getNickName(),attrs.getImgUrl());
        }
        return member;
    }
}
