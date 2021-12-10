package com.ywh.base.common.enums;

/**
 * @author ywh
 * @description
 * @Date 2021/12/10 10:30
 */
public enum ApiCodeEnum {
    CODE_200("200", "请求成功"),
    CODE_400("400", "请求失败")
    ;

    private String code;
    private String msg;

    ApiCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}
