package com.daqsoft.auth;

import com.daqsoft.entity.SysRole;
import com.daqsoft.entity.SysUser;
import com.daqsoft.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 自定义验证组件，设置用户角色权限
 * @author lxx
 */


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;

    public CustomAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        SysUser user = userService.findByUsernameAndPassword(username, password);
        if (null != user){
            // 这里设置权限和角色
            List<GrantedAuthority> authorities = new ArrayList<>();
            List<SysRole> roles = user.getRoleList();
            for (SysRole role : roles) {
                authorities.add(new SimpleGrantedAuthority(role.getRolename()));
            }
            // 生成令牌
            Authentication auth = new UsernamePasswordAuthenticationToken(username, password, authorities);
            return auth;
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
