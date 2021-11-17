package com.ywh.base.common.enums;

/**
 * @author ywh
 * @description
 * @Date 2021/11/17 10:02
 */
public enum StatusCodeEnum {

    CODE_200("200", "成功"),
    CODE_400("400", "请求错误")
    ;

    private String code;
    private String msg;

    StatusCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMs() {
        return msg;
    }
}
