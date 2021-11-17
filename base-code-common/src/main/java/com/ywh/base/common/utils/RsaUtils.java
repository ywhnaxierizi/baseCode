package com.ywh.base.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author ywh
 * @description 非对称加密工具类
 * @Date 2021/11/11 12:10
 */
@Slf4j
@Component
public class RsaUtils {

    /**随机密文*/
    private static final String secret = "comywhbasecode";
    /**密文的长度*/
    private static final int DEFAULT_KEY_SIZE = 1024;
    /**公匙和私匙存放的文件url*/
    @Value("${rsa.publicKeyFile}")
    private String publicKeyFile;
    @Value("${rsa.privateKeyFile}")
    private String privateKeyFile;


    /**
     * 获取公匙
     * @return
     */
    public PublicKey getPublicKey() {
        //从文件中读取信息
        try {
            byte[] publicKeyBytes = readKeyFromFile(publicKeyFile);
            publicKeyBytes = Base64.decodeBase64(publicKeyBytes);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            return factory.generatePublic(spec);
        } catch (Exception e) {
            log.error("从文件获取公匙失败", e);
            return null;
        }
    }


    /**
     * 从文件中获取私匙
     * @return
     */
    public PrivateKey getPrivateKey() {
        try {
            byte[] privateKeyBytes = readKeyFromFile(privateKeyFile);
            privateKeyBytes = Base64.decodeBase64(privateKeyBytes);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            return factory.generatePrivate(spec);
        } catch (Exception e) {
            log.error("从文件获取私匙失败", e);
            return null;
        }
    }

    /**
     * 生成密匙对并写入到文件中
     * @throws Exception
     */
    public void generatorKey() throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        keyPairGenerator.initialize(DEFAULT_KEY_SIZE, secureRandom);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //生成密匙对
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        byte[] publicKeyBytes = Base64.encodeBase64(publicKey.getEncoded());
        byte[] privateKeyBytes = Base64.encodeBase64(privateKey.getEncoded());
        writeKeyToFile(publicKeyFile, publicKeyBytes);
        writeKeyToFile(privateKeyFile, privateKeyBytes);
    }


    /**
     * 将生成的密匙以字节码的形式写入文件
     * @param fileUrl
     * @param key
     * @throws Exception
     */
    private void writeKeyToFile(String fileUrl, byte[] key) throws Exception {
        if (StringUtils.isBlank(fileUrl)) {
            log.error("传入的文件url为空");
            throw new Exception("密匙存入的文件为空");
        }
        File file = new File(fileUrl);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        Files.write(file.toPath(), key);
    }

    /**
     * 从文件中读取密匙
     * @param fileUrl
     * @return
     * @throws Exception
     */
    private byte[] readKeyFromFile(String fileUrl) throws Exception{
        if (StringUtils.isBlank(fileUrl)) {
            log.error("传入的文件url为空");
            throw new Exception("读取密匙的文件路径为空");
        }
        File file = new File(fileUrl);
        if (!file.exists()) {
            log.error("文件不存在");
            return null;
        }
        return Files.readAllBytes(file.toPath());
    }

}
