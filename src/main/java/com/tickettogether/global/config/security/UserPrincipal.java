package com.tickettogether.global.config.security;

import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.domain.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPrincipal implements OAuth2User, UserDetails {

    private Long userId;

    private String userEmail;

    private Role role;

    private Collection<GrantedAuthority> authorities;

    private String userNameAttribute;

    private Map<String, Object> attributes;

    @Override
    public String getName() {
        return userEmail;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    public static UserPrincipal create(Member member) {
        return UserPrincipal.builder()
                .userId(member.getId())
                .userEmail(member.getEmail())
                .role(Role.USER)
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(Role.USER.getKey()))).build();
    }

    public static UserPrincipal create(Member member, String userNameAttribute, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = create(member);
        userPrincipal.setUserNameAttribute(userNameAttribute);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }
}
