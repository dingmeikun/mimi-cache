package com.mimi.spring.cache.service.impl;

import cn.hutool.json.JSONUtil;
import com.mimi.spring.cache.Entity.User;
import com.mimi.spring.cache.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * compositeCache
 *
 * @author Tim
 * @date 2021-09-14
 */
@Slf4j
@Service("compositeCache")
public class CompositeCacheImpl implements CacheService {

    public static final String USER_CACHE_NAME = "USER_INFO";

    @Override
    @Cacheable(value = USER_CACHE_NAME, key = "#user.userID", cacheManager = "caffeineCacheManager")
    public User save(User user) {
        log.info("Service保存用户:{}", JSONUtil.toJsonStr(user));
        return user;
    }

    @Override
    @CacheEvict(value = USER_CACHE_NAME, key = "#userId", cacheManager = "caffeineCacheManager")
    public void remove(Long userId) {
        log.info("Service删除用户ID:{}", userId);
    }

    @Override
    @CacheEvict(value = USER_CACHE_NAME, allEntries = true, cacheManager = "caffeineCacheManager")
    public void removeAll() {
        log.info("Service删除所有用户缓存");
    }

    @Override
    public User findOne(User user) {
        return null;
    }

    @Override
    @Cacheable(value = USER_CACHE_NAME, key = "#userId", cacheManager = "caffeineCacheManager")
    public User findOne(Long userId) {
        log.info("Service查询指定用户{}缓存!", userId);
        return null;
    }

    @Override
    public boolean update(User user) {
        return false;
    }
}
