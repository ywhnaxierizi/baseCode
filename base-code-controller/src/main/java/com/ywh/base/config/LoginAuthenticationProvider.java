package com.ywh.base.config;

import com.ywh.base.common.domain.SysUser;
import com.ywh.base.service.impl.auth.SecurityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author ywh
 * @description 自定义登录认证的prodiver，对传入的用户信息进行验证
 * @Date 2021/11/16 10:21
 */
@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SecurityServiceImpl securityService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        //TODO 这里从数据库里获取用户信息，然后对二者进行比较

        SysUser user = (SysUser) securityService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(user, password);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
