package com.ywh.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author ywh
 * @description redis配置类
 * @Date 2021/11/18 10:56
 */
@Configuration
public class RedisConfig {

    /**参数配置*/
    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private int port;
    @Value("${redis.timeout}")
    private int timeout;
    @Value("${redis.database}")
    private int database;
    @Value("${redis.maxTotal}")
    private int maxTotal;
    @Value("${redis.maxIdle}")
    private int maxIdle;
    @Value("${redis.blockWhenExhausted}")
    private boolean blockWhenExhausted;
    @Value("${redis.maxWaitMillis}")
    private long maxWaitMillis;
    @Value("${redis.minEvictableIdleTimeMillis}")
    private long minEvictableIdleTimeMillis;

    @Bean
    @Primary
    public JedisPoolConfig config() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大空闲数
        jedisPoolConfig.setMaxIdle(maxIdle);
        //连接池最大连接数数据库数量
        jedisPoolConfig.setMaxTotal(maxTotal);
        //连接最大等待时间
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        //逐出连接最小空空闲时间
        jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(false);
        jedisPoolConfig.setTestWhileIdle(true);
        return jedisPoolConfig;
    }


}
