package org.jeecgframework.web.system.core.cache.service;

/**
 * 平台缓存工具类
 * @author qinfeng
 *
 */
public interface CacheService {

	/**
	 * 获取缓存
	 * @param cacheName
	 * @param key
	 * @return
	 */
	Object get(String cacheName, Object key);

	/**
	 * 设置缓存
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	void put(String cacheName, Object key, Object value);

	/**
	 * 移除缓存
	 * @param cacheName
	 * @param key
	 * @return
	 */
	boolean remove(String cacheName, Object key);

	/**
	 * 清空所有缓存
	 */
	void clean();
	
	/**
	 * 清空缓存
	 * @param cacheName
	 */
	void clean(String cacheName);

}
