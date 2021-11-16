package com.ywh.base.filter;

import com.alibaba.fastjson.JSONObject;
import com.ywh.base.common.domain.SysUser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author ywh
 * @description 认证过滤器，因为继承了UsernamePasswordAuthenticationFilter，所以认证过程会先进入此过滤器
 * @Date 2021/11/16 10:58
 */
@Slf4j
public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        log.info("进入登录过滤器");
        //TODO 验证码验证

        SysUser sysUser = JSONObject.parseObject(request.getInputStream(), SysUser.class);
        String username = sysUser.getUsername();
        String password = sysUser.getPassword();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }


    /**
     * 认证成功后的处理
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("用户登录认证成功");
        SysUser sysUser = (SysUser) authResult.getPrincipal();

        //TODO 将相关返回信息参数写到response中
        PrintWriter out = response.getWriter();
        out.write("");
        out.flush();
        out.flush();
    }

    /**
     * 认证失败后的处理
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("用户登录认证失败");
        //TODO 将相关返回信息参数写到response中
        PrintWriter out = response.getWriter();
        out.write("");
        out.flush();
        out.flush();
    }

}
