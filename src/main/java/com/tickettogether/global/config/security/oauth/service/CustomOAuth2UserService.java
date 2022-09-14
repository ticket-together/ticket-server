package com.tickettogether.global.config.security.oauth.service;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.repository.MemberRepository;
import com.tickettogether.global.config.security.UserPrincipal;
import com.tickettogether.global.config.security.oauth.dto.KakaoOAuthAttributes;
import com.tickettogether.global.config.security.oauth.dto.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

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

        try {
            return this.process(userRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId(); //로그인 진행 중인 서비스를 구분하는 코드
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()   //로그인 진행 시 키가 되는 필드값(pk)
                .getUserNameAttributeName();

        OAuthAttributes attr = KakaoOAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        assert attr != null;
        Member member = saveOrUpdateMemberToDB(attr);

        return UserPrincipal.create(member, attr.getNameKey(), attr.getAttributes());
    }

    private Member saveOrUpdateMemberToDB(OAuthAttributes attrs) {
        Member member = memberRepository.findByEmail(attrs.getEmail()).orElse(null);
        if (Optional.ofNullable(member).isEmpty()) {
            return memberRepository.save(attrs.toEntity());
        }

        updateMember(member, attrs);

        if (member.getStatus().equals(Member.Status.INACTIVE)) {
            member.changeStatus(member.getStatus());
        }
        return member;
    }

    private void updateMember(Member member, OAuthAttributes attrs) {

        if(member.getImgUrl() != null & !member.getImgUrl().equals(attrs.getImgUrl())){
            member.updateOAuthProfile(attrs.getImgUrl());
        }
    }
}
