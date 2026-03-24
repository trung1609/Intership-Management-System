package com.trung.security.exception;

import com.trung.security.response.JwtErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import tools.jackson.databind.ObjectMapper;


import java.io.IOException;
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {


    public static final String EXPIRED = "ExpiredJwtException";
    public static final String MALFORMED = "MalformedJwtException";
    public static final String SIGNATURE = "SignatureException";
    public static final String UNSUPPORTED = "UnsupportedJwtException";
    public static final String ILLEGAL = "IllegalArgumentException";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String exception = (String) request.getAttribute("exception");

        if (exception == null) {
            exception = "InvalidTokenException";
        }

        JwtErrorResponse errorResponse = buildErrorResponse(exception);
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
        response.getWriter().flush();
    }

    private JwtErrorResponse buildErrorResponse(String exception) {
        return switch (exception) {
            case EXPIRED -> new JwtErrorResponse("Unauthorized", "Token has expired");
            case MALFORMED -> new JwtErrorResponse("Unauthorized", "Token is malformed");
            case SIGNATURE -> new JwtErrorResponse("Unauthorized", "Invalid token signature");
            case UNSUPPORTED -> new JwtErrorResponse("Unauthorized", "Unsupported token type");
            case ILLEGAL -> new JwtErrorResponse("Unauthorized", "Illegal token");
            default -> new JwtErrorResponse("Unauthorized", "Invalid token or missing token");
        };
    }
}
