package com.highgeupsik.backend.api.jwt;

import static com.highgeupsik.backend.api.controller.ApiUtils.error;
import static javax.servlet.http.HttpServletResponse.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.highgeupsik.backend.api.controller.ApiError;
import com.highgeupsik.backend.core.exception.ErrorMessages;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint { //인증 정보 없을때 401 UNAUTHORIZED

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException {
        response.setStatus(SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(this.objectMapper
            .writeValueAsString(error(new ApiError("UNAUTHORIZED", ErrorMessages.NOT_USER))));
    }
}
