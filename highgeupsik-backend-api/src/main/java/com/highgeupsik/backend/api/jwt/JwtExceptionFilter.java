package com.highgeupsik.backend.api.jwt;

import static com.highgeupsik.backend.api.controller.ApiUtils.error;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.highgeupsik.backend.api.controller.ApiError;
import com.highgeupsik.backend.core.exception.TokenException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class JwtExceptionFilter extends GenericFilterBean {

    private final ObjectMapper mapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (TokenException tokenException) {
            sendErrorMessage((HttpServletResponse) response, tokenException.getMessage());
        }
    }

    private void sendErrorMessage(HttpServletResponse response, String message) throws IOException {
        response.setStatus(SC_BAD_REQUEST);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(mapper.writeValueAsString(error(new ApiError("BAD", message))));
    }
}
