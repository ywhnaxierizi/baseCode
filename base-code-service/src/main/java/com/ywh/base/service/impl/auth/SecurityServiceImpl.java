package com.ywh.base.service.impl.auth;

import com.ywh.base.common.domain.SysUser;
import com.ywh.base.common.domain.User;
import com.ywh.base.service.auth.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ywh
 * @description 自定义的userDetailService实现类，用来从数据库中获取用户信息
 * @Date 2021/11/16 10:25
 */
@Slf4j
@Service
public class SecurityServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUserName(username);
        if (user == null) {
           log.error("未获取到用户信息");
           throw new UsernameNotFoundException("用户账号和密码错误");
        }
        //获取用户的角色权限
        List<GrantedAuthority> list = new ArrayList<>();
        List<String> roles = new ArrayList<>();
        roles.stream().forEach(s -> list.add(new SimpleGrantedAuthority(s)));
        SysUser sysUser = new SysUser(user.getUserName(), user.getPasswd(), list);
        return sysUser;
    }
}
