package com.tickettogether.global.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickettogether.global.config.security.exception.TokenBlackListException;
import com.tickettogether.global.config.security.exception.TokenExpiredException;
import com.tickettogether.global.config.security.exception.TokenValidFailedException;
import com.tickettogether.global.error.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * controller 가 아닌 filter 에서 발생하는 예외 처리
 */
@Slf4j
@Component
public class BaseExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        try {
            filterChain.doFilter(request, response);
        } catch (TokenValidFailedException | TokenExpiredException | TokenBlackListException e) {
            log.error(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write(convertObjectToJson(ErrorResponse.create(e.getErrorCode())));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }
}
