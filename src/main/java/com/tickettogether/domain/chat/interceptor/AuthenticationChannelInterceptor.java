package com.tickettogether.domain.chat.interceptor;
import com.tickettogether.domain.member.domain.Member;
import com.tickettogether.domain.member.exception.UserEmptyException;
import com.tickettogether.domain.member.repository.MemberRepository;
import com.tickettogether.global.config.redis.util.RedisUtil;
import com.tickettogether.global.config.security.exception.TokenBlackListException;
import com.tickettogether.global.config.security.exception.TokenEmptyException;
import com.tickettogether.global.config.security.exception.TokenExpiredException;
import com.tickettogether.global.config.security.exception.TokenValidFailedException;
import com.tickettogether.global.config.security.jwt.token.AuthToken;
import com.tickettogether.global.config.security.jwt.token.AuthTokenProvider;
import com.tickettogether.global.config.security.utils.HeaderUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationChannelInterceptor implements ChannelInterceptor {

    private final RedisUtil<String, String> redisUtil;
    private final AuthTokenProvider authTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null) {
            return null;
        }

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String accessToken = HeaderUtil.getAccessTokenFromWebSocket(accessor);
            String roomId = accessor.getFirstNativeHeader("roomId");

            if (accessToken == null) {
                throw new TokenEmptyException();
            }

            if (redisUtil.hasKeyBlackList(accessToken)) {
                throw new TokenBlackListException();
            }

            AuthToken authToken = authTokenProvider.convertToAuthToken(accessToken);
            try {
                authToken.parseClaims();
            } catch (ExpiredJwtException e) {
                log.error(e.getMessage());
                throw new TokenExpiredException();
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new TokenValidFailedException();
            }

            Authentication authentication = authTokenProvider.getAuthentication(authToken);
            UserDetails principal = (User) authentication.getPrincipal();
            Member member = memberRepository.findById(Long.parseLong(principal.getUsername())).orElseThrow(UserEmptyException::new);

            Objects.requireNonNull(accessor.getSessionAttributes());
            accessor.getSessionAttributes().put("username", member.getName());
            accessor.getSessionAttributes().put("roomId", roomId);
        }
        return message;
    }
}
