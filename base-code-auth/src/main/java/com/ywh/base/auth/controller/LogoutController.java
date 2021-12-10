package com.ywh.base.auth.controller;

import com.ywh.base.common.constants.CommonConstants;
import com.ywh.base.common.enums.ApiCodeEnum;
import com.ywh.base.common.model.Payload;
import com.ywh.base.common.model.UserInfo;
import com.ywh.base.common.response.ResponseMessage;
import com.ywh.base.common.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ywh
 * @description 用户登出
 * @Date 2021/12/10 10:15
 */
@RequestMapping("/user/")
@RestController
@Slf4j
public class LogoutController {

    @Autowired
    private RsaUtils rsaUtils;
    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping("logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String authtoken = CookieUtils.getCookueValue(request, CommonConstants.StringValue.AUTH_TOKEN);
        //清除redis中的token和存储authToken的cookie
        if (StringUtils.isNotBlank(authtoken)) {
            Payload<UserInfo> payload = JwtUtils.getUserInfo(authtoken, rsaUtils.getPublicKey(), UserInfo.class);
            UserInfo userInfo = payload.getUserInfo();
            if (userInfo == null) {
                ResponseUtils.write(response, ApiCodeEnum.CODE_400.getCode(), "authToken解析失败");
            }
            //删除redis中的authToken
            redisUtils.deleteKey(CommonConstants.StringValue.AUTH_TOKEN_FILE + userInfo.getUserId());
            CookieUtils.removeCookie(response, CommonConstants.StringValue.AUTH_TOKEN);
        }
        //清除redis中验证码的缓存和存储验证码的cookie
        String verifyCodeId = CookieUtils.getCookueValue(request, CommonConstants.StringValue.VERIFY_CODE);
        if (StringUtils.isNotBlank(verifyCodeId)) {
            redisUtils.deleteKey(CommonConstants.StringValue.VERIFY_FILE + verifyCodeId);
            CookieUtils.removeCookie(response, CommonConstants.StringValue.VERIFY_CODE);
        }
        ResponseMessage.success();
    }
}
