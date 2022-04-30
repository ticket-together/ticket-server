package com.tickettogether.global.config.security.jwt.token;
import com.tickettogether.global.config.security.exception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class AuthTokenProvider {
    private final Key key;
    private final Date expiry;
    private static final String AUTHORITIES_KEY = "role";

    public AuthTokenProvider(String secret, String expiry){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expiry = new Date(System.currentTimeMillis() + Long.parseLong(expiry));
    }

    public AuthToken createRefreshToken(String id, String secret, Long expiry){
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        Date refreshExpiry = new Date(System.currentTimeMillis() + expiry);
        return new AuthToken(id, key, refreshExpiry);
    }

    public AuthToken createAuthToken(String id, String role){
        return new AuthToken(id, role, key, expiry);
    }

    public AuthToken convertToAuthToken(String token){
        return new AuthToken(token, key);
    }

    public Authentication getAuthentication(AuthToken authToken){
        if (authToken.validate()){
            Claims claims = authToken.getTokenClaims();
            //role
            Collection<? extends GrantedAuthority> authorities = Arrays.stream(new String[] {claims.get(AUTHORITIES_KEY).toString()} )
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            User principal = new User(claims.getSubject(), "", authorities);
            return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
        }else{
            throw new TokenValidFailedException("token expired : Please renew access token");
        }
    }
}
