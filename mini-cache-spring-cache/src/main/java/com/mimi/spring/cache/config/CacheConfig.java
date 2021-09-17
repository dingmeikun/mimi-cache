package com.mimi.spring.cache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * 缓存配置
 *
 * @author Tim
 * @date 2021-09-14
 */
@Configuration
public class CacheConfig {

    /**
     * 缓存枚举，支持PH,CH （举例）
     */
    enum cacheEnum {

        USER_INFO(30, 100, 10000L),
        GOOD_INFO(60, 200, 20000L);

        /**
         * 过期时间
         */
        private long expire;

        /**
         * 初始容量
         */
        private int initSize;

        /**
         * 最大容量
         */
        private long maxSize;

        cacheEnum(long expire, int initSize, long maxSize) {
            this.expire = expire;
            this.initSize = initSize;
            this.maxSize = maxSize;
        }
    }

    /**
     * 构造caffeine cache
     *
     *      这里构造了CacheManager，并管理了两个cache[PH，CH]
     *
     * @return
     */
    @Bean("caffeineCacheManager")
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<Cache> caches = new ArrayList<>();
        for (cacheEnum enums : cacheEnum.values()) {
            caches.add(new CaffeineCache(enums.name(), Caffeine.newBuilder().expireAfterWrite(Duration.ofSeconds(enums.expire)).initialCapacity(enums.initSize).maximumSize(enums.maxSize).build()));
        }
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}
