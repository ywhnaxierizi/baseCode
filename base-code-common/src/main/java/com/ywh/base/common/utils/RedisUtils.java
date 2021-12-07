
package com.ywh.base.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
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


    /**
     * 获取指定的对象的集合
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> List<T> getValueForList(String key, Class<T> tClass) {
        Object value = getValue(key);
        if (tClass == Collection.class) {
            return JSONObject.parseArray(value.toString(), tClass);
        }
        return null;
    }

    /**
     * 获取指定的对象
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T getValue(String key, Class<T> tClass) {
        Object value = getValue(key);
        return JSONObject.parseObject(value.toString(), tClass);
    }

    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

}

