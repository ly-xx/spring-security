package com.daqsoft.business;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

/**
 * 主要用来判断登录用户对应的角色是否有访问资源路径的权限
 * @author lxx
 */

@Service
public class BusinessDecisionManager implements AccessDecisionManager {

    public BusinessDecisionManager() {
    }

    /**
     * 判断角色是否有访问该资源路径的权限
     *
     * @param authentication
     * @param o
     * @param collection
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        if (collection == null) {
            return;
        }
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute attribute = (ConfigAttribute) iterator.next();
            String attr = attribute.getAttribute();
            Iterator auIter = authentication.getAuthorities().iterator();
            while (auIter.hasNext()) {
                GrantedAuthority grant = (GrantedAuthority) auIter.next();
                if (attr.trim().equals(grant.getAuthority().trim())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
