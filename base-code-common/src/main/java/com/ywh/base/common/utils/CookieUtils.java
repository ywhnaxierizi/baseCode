package com.ywh.base.common.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ywh
 * @description cookie操作工具类
 * @Date 2021/11/17 10:46
 */
public class CookieUtils {

    /**
     * 新增cookie到httpServletResponse中
     * @param response
     * @param name cookie的名称
     * @param value cookie的值
     * @param expir cookie的生命周期，单位是秒，为0时周期为浏览器会话期间
     */
    public static void addCookie(HttpServletResponse response, String name, String value, Integer expir) {
        Cookie cookie = new Cookie(name, value);
        //设置作用域
        cookie.setPath("/");
        if (expir > 0) {
            cookie.setMaxAge(expir);
        }
        response.addCookie(cookie);
    }

    /**
     * 获取指定cookie的值
     * @param request
     * @param name  cookie的名称
     * @return  指定cookie的值
     */
    public static String getCookueValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "";
        }
        for (Cookie cookie : cookies) {
            if (StringUtils.isNotBlank(name) && StringUtils.equals(name, cookie.getName())) {
                return cookie.getValue();
            }
        }
        return "";
    }

    /**
     * 从httpServletResponse中删除指定的cookie
     * @param response
     * @param name
     */
    public static void removeCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
