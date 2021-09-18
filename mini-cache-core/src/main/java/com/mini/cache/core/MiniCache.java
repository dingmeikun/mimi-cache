package com.mini.cache.core;

import org.springframework.cache.Cache;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * MiniCache，扩展Cache原生接口
 *
 */
public interface MiniCache extends Cache {

    /**
     * 扩展：是否允许空值
     */
    boolean allowNullValue();

    /**
     * 扩展：获取过期时间
     */
    long getExpiration();

    /**
     * 扩展：增加put key的过期时间
     */
    void put(Object key, @Nullable Object value, long expire);

    /**
     * 获取指定key的缓存，并携带过期时间判断
     *
     * @param key
     * @param valueLoader
     * @param expire
     * @param <T>
     * @return
     */
    @Nullable
    <T> T get(Object key, Callable<T> valueLoader, long expire);

    /**
     * 扩展方法：批量保存对象
     *
     * @param keyValues
     */
    public <K,V> void putList(List<KeyValuePair<K, V>> keyValues);

    /**
     * 扩展方法：批量保存对象
     *
     * @param values
     * @param keyField
     * @param keyType
     * @param <K>
     * @param <V>
     */
    public <K, V> void putList(List<V> values, String keyField, Class<K> keyType);

    /**
     * hash set操作
     * @param key
     * @param field
     * @param value
     */
    void hset(final String key, final String field, final Object value);

    /**
     * hash get操作
     * @param key
     * @param field
     * @return
     */
    <T> T hget(final String key, final String field);

    /**
     * 获取wrapper
     * @param key
     * @param field
     * @return
     */
    ValueWrapper hgetWrapper(final String key, final String field);

    /**
     * 是否存在
     * @param key
     * @param field
     * @return
     */
    boolean hexists(final String key, String field);

    /**
     * 批量 set
     * @param key
     * @param hash
     */
    <T> void hmset(final String key, final Map<String, T> hash);

    /**
     * 批量 del
     * @param key
     * @param fields
     */
    void hdel(final String key, final String... fields);

    /**
     * 获取所有value
     * @param key
     * @return
     */
    <T> List<T> hvals(final String key);

    /**
     * 获取所有kv
     * @param key
     * @return
     */
    <T> Map<String, T> hgetAll(final String key);

    /**
     * 获取集合大小
     * @param key
     * @return
     */
    long hlen(final String key);

    /**
     * 获取所有field
     * @param key
     * @return
     */
    Set<String> hkeys(final String key);
}
