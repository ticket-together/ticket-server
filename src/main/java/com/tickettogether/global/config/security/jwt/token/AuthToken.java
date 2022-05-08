package com.tickettogether.global.config.security.jwt.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;

@Slf4j
@Getter
@RequiredArgsConstructor
public class AuthToken {
    private final String token;
    private final Key key;
    private static final String AUTHORITIES_KEY = "role";

    public AuthToken(String id, Key key, Date expiry){
        this.key = key;
        this.token = createToken(id, expiry);
    }

    public AuthToken(String id, String role, Key key, Date expiry){
        this.key = key;
        this.token = createToken(id, role, expiry);
    }

    private String createToken(String id, Date expiry){
        return Jwts.builder()
                .setSubject(id)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    private String createToken(String id, String role, Date expiry){
        return Jwts.builder()
                .setSubject(id)
                .claim(AUTHORITIES_KEY, role)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    public boolean validate() {
        return this.getTokenClaims() != null;
    }
    //토큰 Claim 가져오기
    public Claims getTokenClaims(){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (SecurityException e) {
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        }
        return null;
    }

    public Claims getRefreshTokenClaims(String accessToken){
        try{
            Key new_key = Keys.hmacShaKeyFor(accessToken.getBytes());
            return Jwts.parserBuilder()
                    .setSigningKey(new_key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (SecurityException e) {
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        }
        return null;
    }

    //만료된 토큰 Claim 가져오기
    public Claims getExpiredTokenClaims() {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            return e.getClaims();
        }
        return null;
    }

    public long getRemainMilliSeconds() {
        Date expiration = getTokenClaims().getExpiration();
        Date now = new Date();
        return expiration.getTime() - now.getTime();
    }

    public long getRemainMilliSeconds(String refreshToken) {
        Date expiration = getRefreshTokenClaims(refreshToken).getExpiration();
        Date now = new Date();
        return expiration.getTime() - now.getTime();
    }
}
