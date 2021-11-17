package com.ywh.base.config;

import com.ywh.base.common.model.SysUser;
import com.ywh.base.common.utils.Md5Utils;
import com.ywh.base.service.impl.auth.SecurityServiceImpl;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
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

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        SysUser sysUser = (SysUser) securityService.loadUserByUsername(username);
        /*if (!sysUser.isEnabled()) {
            throw new DisabledException("用户禁用");
        }
        if (!sysUser.isAccountNonExpired()) {
            throw new AccountExpiredException("账号过期");
        }
        if (!sysUser.isAccountNonLocked()) {
            throw new LockedException("账号锁定");
        }
        if (!sysUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("凭证过期");
        }*/
        //对用户密码进行验证,数据库中的密码需要用MD5加密,所以这里先对密码进行加密后进行对比
        password = Md5Utils.mdsEncode(password);
        if (!StringUtils.equals(password, sysUser.getPassword())) {
            throw new BadCredentialsException("用户密码和账号错误");
        }
        return new UsernamePasswordAuthenticationToken(sysUser, password);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
