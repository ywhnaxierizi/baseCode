package com.ywh.base.common.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author ywh
 * @description security中进行参数传递的用户实体
 * @Date 2021/11/16 15:04
 */
@Data
public class SysUser implements UserDetails {

    /**用户的属性需要和传入的一致，和自定义用户认证过滤器AuthUserPasswdAuthenFilter也要保持一致*/
    private String username;
    private String password;
    /**用户的权限列表*/
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * 构造器
     * @return
     */
    public SysUser(){}

    public SysUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
