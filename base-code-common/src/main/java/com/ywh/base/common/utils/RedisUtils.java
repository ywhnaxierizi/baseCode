
package com.ywh.base.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


/**
 * @author ywh
 * @description redis操作工具类
 * @Date 2021/11/19 13:09
 */

@Slf4j
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 设值
     * @param key
     * @param value
     */
    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, JSONObject.toJSONString(value));
    }


    /**
     * 设置值并设置过期时间
     * @param key
     * @param value
     * @param timeout
     * @param unit
     */
    public void setValue(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }


}

