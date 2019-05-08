package org.jeecgframework.core.common.service;


import java.util.Map;

/**
 * RedisService工具类
 * @author Administrator
 * @date 2019-05-08
 * @version V1.0
 */
public interface RedisService {

    /**
     * 根据key获取value
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 从redis中获取map
     * @param key
     * @return
     */
    Map<String, Object> getMap(String key);

    /**
     * 将key和value存入redis,并设置有效时间,单位: 天
     * @param key
     * @param value
     * @param timeout
     */
    void set(String key, String value, long timeout);

    /**
     * 将key和value存入redis
     * @param key
     * @param value
     */
    void set(String key, String value);

    /**
     * 将map存入redis,并设置时效
     * @param key
     * @param map
     * @param timeout
     */
    void set(String key, Map<? extends String, ? extends Object> map, long timeout);

    /**
     * 删除key和value
     * @param key
     */
    void delete(String key);
}
