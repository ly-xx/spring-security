package com.daqsoft.business;

import com.daqsoft.service.SysOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。 此类在初始化时，应该取到所有资源及其对应角色的定义。
 *
 * @author lxx
 */

@Service
public class BusinessMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private SysOperateService operateService;

    //各个角色对应访问资源
    private static Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<>();


    public BusinessMetadataSource() {
    }

    @PostConstruct
    private void loadResourceDefine() {
        List<Map<String, Object>> operates = this.operateService.queryForAuthorization();
        operates.forEach((operate) -> {
            String url = operate.get("url").toString();
            Object codeTemp = operate.get("rolename");
            String code = codeTemp == null ? "ROLE_ADMIN" : codeTemp.toString();
            ConfigAttribute attribute = new SecurityConfig(code);
            Collection<ConfigAttribute> attributes;
            if (resourceMap.containsKey(url)) {
                attributes = resourceMap.get(url);
            } else {
                attributes = new ArrayList();
                resourceMap.put(url, attributes);
            }
            (attributes).add(attribute);
        });
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取请求路径
        FilterInvocation filterInvocation = (FilterInvocation) object;
        String url = filterInvocation.getRequestUrl();
        url = url.split("\\?")[0];
        boolean authority = resourceMap.containsKey(url);
        if (authority) {
            System.out.println("验证通过，进行跳转");
            return resourceMap.get(url);
        } else {
            System.out.println("验证失败");
            throw new AccessDeniedException(String.format("url: '%s' 不允许访问", new Object[]{url}));
        }
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return new ArrayList();
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
