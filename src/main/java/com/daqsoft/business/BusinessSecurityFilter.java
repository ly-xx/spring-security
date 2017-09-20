//package com.daqsoft.business;
//
//import com.daqsoft.commons.responseEntity.ResponseBuilder;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.access.SecurityMetadataSource;
//import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
//import org.springframework.security.access.intercept.InterceptorStatusToken;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.servlet.*;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @author lxx
// */
//
////@Component
//public class BusinessSecurityFilter extends AbstractSecurityInterceptor implements Filter {
//
//    @Autowired
//    private BusinessMetadataSource mySecurityMetadataSource;
//    @Autowired
//    private BusinessDecisionManager myAccessDecisionManager;
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @PostConstruct
//    public void init(FilterConfig filterConfig) throws ServletException {
//        super.setAuthenticationManager(this.authenticationManager);
//        super.setAccessDecisionManager(this.myAccessDecisionManager);
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        FilterInvocation filterInvocation = new FilterInvocation(request, response, chain);
//        InterceptorStatusToken token = null;
//        try {
//            token = super.beforeInvocation(filterInvocation);
//            chain.doFilter(request, response);
//        } catch (AccessDeniedException | AuthenticationException var12) {
//            if(this.logger.isDebugEnabled()) {
//                this.logger.debug("", var12);
//            }
//
//            int unauthorizedCode = HttpStatus.UNAUTHORIZED.value();
//            response.setContentType("application/json;charset=UTF-8");
//            ((HttpServletResponse)response).setStatus(HttpStatus.UNAUTHORIZED.value());
//            this.objectMapper.writeValue(response.getWriter(), ResponseBuilder.custom().failed(var12.getMessage(), unauthorizedCode).build());
//        } catch (Exception var13) {
//            if(this.logger.isDebugEnabled()) {
//                this.logger.debug("", var13);
//            }
//
//            response.setContentType("application/json;charset=UTF-8");
//            ((HttpServletResponse)response).setStatus(HttpStatus.UNAUTHORIZED.value());
//            this.objectMapper.writeValue(response.getWriter(), ResponseBuilder.custom().failed("internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
//        } finally {
//            super.afterInvocation(token, (Object)null);
//        }
//    }
//
//    @Override
//    public void destroy() {
//        System.out.println("销毁资源");
//    }
//
//    @Override
//    public Class<FilterInvocation> getSecureObjectClass() {
//        return FilterInvocation.class;
//    }
//
//    @Override
//    public SecurityMetadataSource obtainSecurityMetadataSource() {
//        System.out.println("获取资源");
//        return this.mySecurityMetadataSource;
//    }
//}
