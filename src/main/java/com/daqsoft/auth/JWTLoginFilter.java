package com.daqsoft.auth;

import com.daqsoft.commons.responseEntity.ResponseBuilder;
import com.daqsoft.utils.RequestUtils;
import com.daqsoft.utils.WebUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lxx
 */


public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {

        String requestMethod = request.getMethod();
        if(HttpMethod.POST.name().equals(requestMethod) && WebUtil.isAjax(request)) {
            JwtUserDetail loginRequest = (JwtUserDetail) RequestUtils.getLoginRequest(request, JwtUserDetail.class);
            if(loginRequest != null && !StringUtils.isBlank(loginRequest.getUsername()) && !StringUtils.isBlank(loginRequest.getPassword())) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
                return this.getAuthenticationManager().authenticate(token);
            } else {
                if(logger.isDebugEnabled()) {
                    logger.debug("帐号或密码为空");
                }

                throw new AuthenticationServiceException("账号或密码为空！");
            }
        } else {
            if(logger.isDebugEnabled()) {
                logger.debug("请求方法不支持. 请求方法: " + requestMethod);
            }
            throw new AuthenticationServiceException("有错误了");
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        JwtTokenFactory.addAuthentication(res, auth);
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        new ObjectMapper().writeValue(response.getWriter(), ResponseBuilder.custom().failed("权限认证失败").build());
    }
}
