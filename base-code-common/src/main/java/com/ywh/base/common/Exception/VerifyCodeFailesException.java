package com.ywh.base.common.Exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author ywh
 * @description 验证码验证异常
 * @Date 2021/11/17 12:58
 */
public class VerifyCodeFailesException extends AuthenticationException {
    public VerifyCodeFailesException(String msg, Throwable t) {
        super(msg, t);
    }

    public VerifyCodeFailesException(String msg) {
        super(msg);
    }
}
