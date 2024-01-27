package com.ssginc.secretgarden.global.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        String key = "ThisIsTestKeyThisIsTestKeyThisIsTestKeyThisIsTestKeyThisIsTestKeyThisIsTestKey";
        Key secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        if (token != null && token.startsWith("Bearer ")) {
            try {
                String jwtToken = token.substring(7);
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(secretKey).build()
                        .parseClaimsJws(jwtToken)
                        .getBody();
                return true;
            } catch (ExpiredJwtException e) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token not provided");
                return false;
            }
        }
        sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token not provided");
        return false;
    }

    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws Exception {
        response.setStatus(statusCode);
        response.getWriter().write(message);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
