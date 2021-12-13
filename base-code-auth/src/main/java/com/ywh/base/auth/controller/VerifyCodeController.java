package com.ywh.base.auth.controller;

import ch.qos.logback.core.util.TimeUtil;
import com.alibaba.excel.util.StringUtils;
import com.ywh.base.common.constants.CommonConstants;
import com.ywh.base.common.utils.CookieUtils;
import com.ywh.base.common.utils.RedisUtils;
import com.ywh.base.common.utils.VerificationCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ywh
 * @description
 * @Date 2021/11/10 17:24
 */
@RestController
public class VerifyCodeController {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 获取验证码
     * @param request
     * @param response
     */
    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        response.setDateHeader("expries", -1);
        response.setHeader("Cache-control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        String verifyCode = VerificationCodeUtils.generateVerifyCode(4).toLowerCase();
        //将验证码id存入到cookie中返回给页面
        String verifyCodeId = CookieUtils.getCookueValue(request, CommonConstants.StringValue.VERIFY_CODE);
        //如果request中不存在验证码的cookie，则加入其中
        if (StringUtils.isBlank(verifyCodeId)) {
            verifyCodeId = UUID.randomUUID().toString().replace("-", "");
            CookieUtils.addCookie(response, CommonConstants.StringValue.VERIFY_CODE, verifyCodeId, CommonConstants.ActiveTime.MINUTES_10);
        }
        //将验证码存入到redis中，有效时间设置为10分钟
        redisUtils.deleteKey(verifyCodeId);
        redisUtils.setValue( CommonConstants.StringValue.VERIFY_FILE+ verifyCodeId.toString(), verifyCode, 10, TimeUnit.MINUTES);
        VerificationCodeUtils.outputImage(80, 50, response.getOutputStream(), verifyCode);
    }
}
