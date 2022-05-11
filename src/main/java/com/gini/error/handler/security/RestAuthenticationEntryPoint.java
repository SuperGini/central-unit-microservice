package com.gini.error.handler.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gini.error.code.ErrorCodes;
import com.gini.error.response.RestErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.addHeader("Content-Type", "application/json;charset=UTF-8");

        RestErrorResponse errorResponse = new RestErrorResponse(
                ErrorCodes.UNAUTHORIZED.toString(),
                ErrorCodes.UNAUTHORIZED.getMessage(),
                List.of()
        );

        objectMapper.writeValue(response.getOutputStream(), errorResponse);
        response.flushBuffer();
    }
}
