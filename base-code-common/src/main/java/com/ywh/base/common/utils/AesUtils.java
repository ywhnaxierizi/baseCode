package com.ywh.base.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

/**
 * @author ywh
 * @description 对称加密算法，前端对用户密码进行加密，防止密码明文在网络上流传
 * 加密和解密的模式以及使用的加密密匙需要一致
 * @Date 2021/11/17 11:20
 */
@Slf4j
public class AesUtils {

    private static String KEY = "";
    private static String IV = "";


    /**
     * 对数据进行加密
     * @param data
     * @return
     */
    public static String encrypt(String data) throws Exception {
        if (StringUtils.isBlank(data)) {
            log.error("传入的数据为空，加密失败");
            return null;
        }
        //设置加密模式，这里使用ECB模式，CBC模式需要IV
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] dataBytes = data.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        //CBC方式
       /* IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);*/
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] bytes = cipher.doFinal(dataBytes);
        return new String(Base64.encodeBase64(bytes));
    }


    /**
     * 解密
     * @param data
     * @return
     * @throws Exception
     */
    public static String decrypt(String data) throws Exception {
        if (StringUtils.isBlank(data)) {
            log.error("传入的数据为空，解密失败");
            return null;
        }
        byte[] dataBytes = Base64.decodeBase64(data.getBytes());
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        //CBC方式
       /* IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);*/
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] bytes = cipher.doFinal(dataBytes);
        return new String(bytes);
    }
}
