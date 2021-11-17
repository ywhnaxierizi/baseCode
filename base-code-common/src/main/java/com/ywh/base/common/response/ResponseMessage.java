package com.ywh.base.common.response;

import com.ywh.base.common.enums.StatusCodeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.common.Mapper;

import javax.management.ObjectName;

/**
 * @author ywh
 * @description 统一返回给前端的类
 * @Date 2021/11/16 22:29
 */
@Data
@NoArgsConstructor
public class ResponseMessage {

    /**返回状态码*/
    private String code;
    /**返回信息*/
    private String message;
    /**返回数据*/
    private Object data;

    public ResponseMessage(Object data) {
        this(StatusCodeEnum.CODE_200.getCode(), StatusCodeEnum.CODE_200.getMs(), data);
    }

    public ResponseMessage(String message) {
        this(StatusCodeEnum.CODE_200.getCode(), message);
    }

    public ResponseMessage(String message, Object data) {
        this(StatusCodeEnum.CODE_200.getCode(), message, data);
    }

    public ResponseMessage(String code, String message) {
        this(code, message, null);
    }

    public ResponseMessage(String code,String message,Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public static ResponseMessage success() {
        return ResponseMessage.success(StatusCodeEnum.CODE_200.getCode(), StatusCodeEnum.CODE_200.getMs());
    }

    public static ResponseMessage success(String message) {
        return ResponseMessage.success(StatusCodeEnum.CODE_200.getCode(), message);
    }

    public static ResponseMessage success(String code, String message) {
        return ResponseMessage.success(code, message, null);
    }

    public static ResponseMessage success(Object data) {
        return ResponseMessage.success(StatusCodeEnum.CODE_200.getCode(), StatusCodeEnum.CODE_200.getMs(), data);
    }

    public static ResponseMessage success(String code, String message, Object data) {
        return new ResponseMessage(code, message, data);
    }


    public static ResponseMessage error() {
        return ResponseMessage.error(StatusCodeEnum.CODE_400.getCode(), StatusCodeEnum.CODE_400.getMs());
    }

    public static ResponseMessage error(String message) {
        return ResponseMessage.error(StatusCodeEnum.CODE_400.getCode(), message);
    }

    public static ResponseMessage error(String code, String message) {
        return new ResponseMessage(code, message);
    }
}
