package com.mimi.spring.cache.service.impl;

import cn.hutool.json.JSONUtil;
import com.mimi.spring.cache.Entity.User;
import com.mimi.spring.cache.config.localValueLoader;
import com.mimi.spring.cache.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * caffeineCache
 *
 * @author Tim
 * @date 2021-09-14
 */
@Slf4j
@Service("caffeineCache")
public class CaffeineCacheImpl implements CacheService {

    public static final String USER_CACHE_NAME = "USER_INFO";
    public static final String USER_CACHE_KEY = "USER_KEY";

    Cache cache;

    @Autowired
    private CacheManager cacheManager;

    @PostConstruct
    public void postConstruct() {
        cache = cacheManager.getCache(USER_CACHE_NAME);
    }

    @Override
    public User save(User user) {
        cache.put(USER_CACHE_KEY + user.getUserID(), user);
        return user;
    }

    @Override
    public void remove(Long userId) {
        log.info("caffeine删除用户:{}", userId);
        cache.evict(USER_CACHE_KEY + userId);
    }

    @Override
    public void removeAll() {
        cache.clear();
    }

    @Override
    public User findOne(User user) {
        User u = cache.get(USER_CACHE_KEY + user.getUserID(), User.class);
        log.info("caffeine1查询用户{}:{}", user.getUserID(), JSONUtil.toJsonStr(u));
        return u;
    }

    /**
     * 请求获取User，首先请求缓存(只有一层Caffeine本地缓存)，如果没有找到则调用localValueLoader获取DB的数据
     *
     * @param userId
     * @return
     */
    @Override
    public User findOne(Long userId) {
        log.info("caffeine2查询用户:{}", userId);
        User user = cache.get(USER_CACHE_KEY + userId, new localValueLoader<>(userId));
        return user;
    }

    @Override
    public boolean update(User user) {
        return true;
    }
}
