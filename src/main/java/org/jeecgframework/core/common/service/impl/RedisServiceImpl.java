package org.jeecgframework.core.common.service.impl;


import org.jeecgframework.core.common.service.RedisService;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * RedisService工具类
 * @author Administrator
 * @date 2019-05-08
 * @version V1.0
 */
@Service(value = "redisService")
public class RedisServiceImpl implements RedisService {

    @Resource
    private StringRedisTemplate redisTemplate;

    @Override
    public String get(String key){
        String value = redisTemplate.opsForValue().get(key);
        return value;
    }

    @Override
    public Map<String, Object> getMap(String key){
        HashOperations<String, String, Object>  hash = redisTemplate.opsForHash();
        Map<String,Object> map = hash.entries(key);
        return map;
    }

    @Override
    public void set(String key, String value, long timeout){
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.DAYS);
    }

    @Override
    public void set(String key, String value){
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Map<? extends String, ? extends Object> map, long timeout){
        redisTemplate.opsForHash().putAll(key, map);
        redisTemplate.expire(key, timeout, TimeUnit.DAYS);
    }

    @Override
    public void delete(String key){
        redisTemplate.delete(key);
    }

}
