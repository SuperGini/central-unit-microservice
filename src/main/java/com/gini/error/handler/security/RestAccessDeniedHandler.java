package com.gini.error.handler.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gini.error.code.ErrorCodes;
import com.gini.error.response.RestErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.addHeader("Content-Type", "application/json;charset=UTF-8");

        RestErrorResponse errorResponse = new RestErrorResponse(
                ErrorCodes.FORBIDDEN.toString(),
                ErrorCodes.FORBIDDEN.getMessage(),
                List.of()
        );

        objectMapper.writeValue(response.getOutputStream(), errorResponse);
        response.flushBuffer();
    }
}
