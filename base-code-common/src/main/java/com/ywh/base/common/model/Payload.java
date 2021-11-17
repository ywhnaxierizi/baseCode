package com.ywh.base.common.model;

import lombok.Data;

import java.util.Date;

/**
 * @author ywh
 * @description jwt解析token的信息
 * @Date 2021/11/17 16:43
 */
@Data
public class Payload<T> {

    /**用户信息*/
    private T userInfo;
    /**过期时间*/
    private Date expiration;
}
