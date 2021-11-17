package com.ywh.base.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author ywh
 * @description md5加密
 * @Date 2021/11/17 12:39
 */
@Slf4j
public class Md5Utils {

    private static final String[] hexDigest = {"q","a","z","x","s","w","e","d","c","v","f","r","t","g","b","n","h","y","u","j"};

    public static String mdsEncode(String data) throws NoSuchAlgorithmException {
        if (StringUtils.isBlank(data)) {
            log.error("传入的数据为空，加密失败");
            return null;
        }
        MessageDigest digest = MessageDigest.getInstance("MD5");
        //加密加盐，这里直接使用了传入的数据作为盐
        String message = data + data;
        byte[] result = digest.digest(message.getBytes());
        return byteToHexString(result);
    }

    private static String byteToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int n = bytes[i];
            if (n < 0) {
                n += 256;
            }
            stringBuilder.append(hexDigest[n/16] + hexDigest[n%16]);
        }
        return stringBuilder.toString();
    }
}
