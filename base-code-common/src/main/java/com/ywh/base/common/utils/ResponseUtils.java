package com.ywh.base.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.ywh.base.common.enums.StatusCodeEnum;
import com.ywh.base.common.response.ResponseMessage;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author ywh
 * @description 将返回数据和提示信息写到response的工具类
 * @Date 2021/11/17 09:19
 */
@Slf4j
public class ResponseUtils {

    public static void write(HttpServletResponse response, Integer status, String dataStatus, String message, Object obj) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setStatus(status);
            PrintWriter out = response.getWriter();
            out.write(JSONObject.toJSONString(ResponseMessage.success(dataStatus, message, obj)));
            out.flush();
            out.flush();
        } catch (Exception e) {
            log.error("响应错误", e);
        }
    }

    /**
     * 直接返回状态和信息，标明接口是正常的
     * @param response
     * @param dataStatus
     * @param message
     */
    public static void write(HttpServletResponse response, String dataStatus, String message) {
        write(response, HttpServletResponse.SC_OK, dataStatus, message, null);
    }

    /**
     * 返回响应状态码，标明接口调用异常
     * @param response
     * @param status
     */
    public static void write(HttpServletResponse response, Integer status) {
        write(response, status, StatusCodeEnum.CODE_200.getCode(), StatusCodeEnum.CODE_200.getMs(), null);
    }

}
