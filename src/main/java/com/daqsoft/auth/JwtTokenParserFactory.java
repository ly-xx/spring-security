package com.daqsoft.auth;

import com.daqsoft.commons.responseEntity.ResponseBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lxx
 */

public class JwtTokenParserFactory {

    static Authentication getAuthentication(HttpServletRequest request) {
        // 从Header中拿到token
        String token = request.getHeader(JwtSetting.HEADER_STRING);

        if (token != null) {
            // 解析 Token
            Claims claims = Jwts.parser()
                    // 验签
                    .setSigningKey(JwtSetting.SECRET)
                    // 去掉 Bearer
                    .parseClaimsJws(token.replace(JwtSetting.TOKEN_PREFIX, ""))
                    .getBody();

            // 拿用户名
            String user = claims.getSubject();

            // 得到 权限（角色）
            List<Map<String, String>> resultList = (List<Map<String, String>>)claims.get("authorities");
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (Map<String, String> map : resultList) {
                authorities.add(new GrantedAuthorityImpl(map.get("authority")));
            }
            // 返回验证令牌
            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null,authorities) :
                    null;
        }
        return null;
    }
}
