package com.ywh.base.common.Exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author ywh
 * @description 用户认证的异常
 * @Date 2021/11/17 14:23
 */
public class AuthException extends AuthenticationException {
    public AuthException(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthException(String msg) {
        super(msg);
    }
}
