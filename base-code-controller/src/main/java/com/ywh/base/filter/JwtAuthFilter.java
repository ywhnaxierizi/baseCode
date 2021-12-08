package com.ywh.base.filter;

import com.alibaba.fastjson.JSONObject;
import com.ywh.base.common.Exception.AuthException;
import com.ywh.base.common.Exception.VerifyCodeFailesException;
import com.ywh.base.common.constants.CommonConstants;
import com.ywh.base.common.enums.StatusCodeEnum;
import com.ywh.base.common.model.SysUser;
import com.ywh.base.common.model.UserInfo;
import com.ywh.base.common.utils.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
        log.info("进入登录过滤器");
        //验证码的id从cookie中获取，通过id从redis获取验证码
        String verifyCodeId = CookieUtils.getCookueValue(request, CommonConstants.VERIFY_CODE);
        if (StringUtils.isBlank(verifyCodeId)) {
            log.error("未获取到verifycode的cookie");
            throw new VerifyCodeFailesException("未获取到verifycode信息");
        }
        // TODO 验证码的值可以放在redis中，从redis中获取验证码，和用户传入的验证码进行比对
        //verifyCode要从redis中获取
        String verifyCode = verifyCodeId;
        SysUser sysUser = JSONObject.parseObject(request.getInputStream(), SysUser.class);
        if (sysUser == null) {
            log.error("用户信息为空");
            throw new UsernameNotFoundException("未获取到用户传入的登录信息");
        }
        String username = sysUser.getUsername();
        String password = sysUser.getPassword();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            log.error("用户信息存在空值");
            throw new UsernameNotFoundException("未获取到用户传入的登录信息");
        }
        if (StringUtils.equals(verifyCode, sysUser.getVerifyCode())) {
            log.error("验证码不正确");
            throw new VerifyCodeFailesException("验证码错误");
        }

        //这里是请求传来的password,是经过AES加密过的，所以需要先加密后再与数据库中的进行对比
        try {
            password = AesUtils.decrypt(password);
        } catch (Exception e) {
            log.error("密码解密失败");
            throw new AuthException("系统错误，请联系技术人员");
        }
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
        //用户认证成功后要生成token
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(sysUser.getUserId());
        userInfo.setUserName(sysUser.getUsername());
        userInfo.setNickName(sysUser.getNickName());
        /**因为RsaUtils要从配置中读取参数，所以方法不能写成静态的，本类没有被spring容器管理，所以这里
         * 无法加载RsaUtils,只能通过spring上下文容器来获取
         */
        RsaUtils rsaUtils = SpringContextBeanUtils.getBean(RsaUtils.class);
        String token = JwtUtils.createToken(rsaUtils.getPrivateKey(), userInfo, 8*60);
        // TODO 可以将token写到reids

        //将token写到cookie中
        Map<String, Object> data = new HashMap<>();
        data.put("userInfo", userInfo);
        data.put("token", token);
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        response.addCookie(cookie);
        ResponseUtils.write(response, HttpServletResponse.SC_OK, StatusCodeEnum.CODE_200.getCode(), "用户认证成功", data);
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
        SecurityContextHolder.clearContext();
        if (failed instanceof InternalAuthenticationServiceException) {
            log.error("认证服务不正常");
        } else if (failed instanceof UsernameNotFoundException) {
            log.error("用户账号或密码错误");
        } else if (failed instanceof BadCredentialsException) {
            log.error("用户账号或密码错误");
        } else if (failed instanceof AccountExpiredException) {
            log.error("账号过期");
        }  else if (failed instanceof LockedException) {
            log.error("账号被锁");
        }  else if (failed instanceof CredentialsExpiredException) {
            log.error("密码失效");
        }  else if (failed instanceof DisabledException) {
            log.error("账号被锁");
        }
        ResponseUtils.write(response,HttpServletResponse.SC_BAD_REQUEST);
    }

}
