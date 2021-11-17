package com.ywh.base.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.ywh.base.common.model.Payload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author ywh
 * @description jwt工具类，用来生成token
 * @Date 2021/11/10 12:54
 */
@Slf4j
public class JwtUtils {

    private static final String USER_INFO = "USER_INFO";

    /**
     * 使用私匙生成token
     * @param privateKey 使用私匙生成token
     * @param userInfo
     * @param exptime 过期设置时长，这里单位是分钟，根据需要变更
     * @return
     */
    public static String createToken(PrivateKey privateKey, Object userInfo, int exptime) {
        //获取失效时间
        Date expiration = Date.from(LocalDateTime.now().plusMinutes(exptime).atZone(ZoneId.systemDefault()).toInstant());
        String token = Jwts.builder()
                        .claim(USER_INFO, JSONObject.toJSONString(userInfo))
                        .setIssuedAt(new Date())
                        .setExpiration(expiration)
                        .signWith(SignatureAlgorithm.RS256, privateKey)
                        .compact();
        return token;
    }


    /**
     * 解析token，获取用户信息
     * @param token
     * @param publicKey
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> Payload<T> getUserInfo(String token, PublicKey publicKey, Class<T> tClass) {
        Claims claims = parseToken(token, publicKey);
        Payload<T> payload = new Payload<>();
        payload.setExpiration(claims.getExpiration());
        payload.setUserInfo(JSONObject.parseObject(claims.get(USER_INFO).toString(), tClass));
        return payload;
    }

    /**
     * 解析token，使用
     * @return
     */
    private static Claims parseToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
    }


}
