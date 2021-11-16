package com.ywh.base.service.impl.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author ywh
 * @description 自定义的userDetailService实现类，用来从数据库中获取用户信息
 * @Date 2021/11/16 10:25
 */
@Service
public class SecurityServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
