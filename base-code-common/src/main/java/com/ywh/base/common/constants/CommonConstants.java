package com.ywh.base.common.constants;

/**
 * @author ywh
 * @description 通用基础信息
 * @Date 2021/11/17 12:49
 */
public class CommonConstants {



    /**生效时间设置*/
    public static class ActiveTime {
        public static final int DAY_1 = 24 * 60 * 60;
        public static final int MINUTES_10 = 10 * 60;
    }

    public static class StringValue {
        /**存放验证码的cookie的id*/
        public static final String VERIFY_CODE = "verifycode";
        public static final String VERIFY_FILE = "verify:";
        /**存放authtoken*/
        public static final String AUTH_TOKEN_FILE = "authtoken:";
    }
}
