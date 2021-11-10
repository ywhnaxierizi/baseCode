package com.ywh.base.controller.auth;

import com.ywh.base.common.utils.VerificationCodeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ywh
 * @description
 * @Date 2021/11/10 17:24
 */
@RestController
@RequestMapping("/auth/")
public class VerifyCodeController {


    /**
     * 获取验证码
     * @param request
     * @param response
     */
    @GetMapping("verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        response.setDateHeader("expries", -1);
        response.setHeader("Cache-control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        String verifyCode = VerificationCodeUtils.generateVerifyCode(4).toLowerCase();
        VerificationCodeUtils.outputImage(80, 50, response.getOutputStream(), verifyCode);
    }
}
