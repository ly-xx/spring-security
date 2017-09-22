package com.daqsoft.auth;

import com.daqsoft.commons.responseEntity.ResponseBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lxx
 */

public class JwtTokenFactory {

    static void addAuthentication(HttpServletResponse response, Authentication auth) {
        // 生成JWT
        String JWT = Jwts.builder()
                // 保存权限（角色）
                .claim("authorities", auth.getAuthorities())
                // 用户名写入标题
                .setSubject(auth.getName())
                // 有效期设置
                .setExpiration(new Date(System.currentTimeMillis() + JwtSetting.EXPIRATIONTIME))
                // 签名设置
                .signWith(SignatureAlgorithm.HS512, JwtSetting.SECRET)
                .compact();

        Map<String, Object> tokenMap = new HashMap();
        tokenMap.put("token", JWT);
        // 将 JWT 写入 body
        try {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            new ObjectMapper().writeValue(response.getWriter(), ResponseBuilder.custom().data(tokenMap).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
