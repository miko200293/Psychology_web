package com.oliver.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oliver.utils.Jwtutils;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;
@Component
public class JwtInterceptors implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> map =new HashMap<>();
        String token = request.getHeader("token");
        try {
            if (Jwtutils.checkToken(token)) {
                // 从令牌中获取用户信息，进行后续操作
                Claims claims = Jwtutils.getClaimsByToken(token);
                String Username = claims.getSubject();
                // 根据用户名执行相应的逻辑
                map.put("msg", "yes");
                return true;
            } else {
                map.put("msg","Unauthorized: Invalid or expired token.");

            }
        } catch (ExpiredJwtException ex) {
            map.put("msg","Unauthorized: Token expired");

        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            map.put("msg","Unauthorized: Invalid token..");

        }
        String s = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(s);
        return false;

    }
}
