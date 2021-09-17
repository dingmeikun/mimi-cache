package com.mimi.spring.cache.service.impl;

import cn.hutool.json.JSONUtil;
import com.mimi.spring.cache.Entity.User;
import com.mimi.spring.cache.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * springCache
 *
 * @author Tim
 * @date 2021-09-14
 */
@Slf4j
@Primary
@Service("springCache")
public class SpringCacheImpl implements CacheService {

    @Override
    @Cacheable(value = "cache:user" /**key = "#user.userID" 不填key缺省为全属性拼接*/)
    public User save(User user) {
        log.info("Service保存用户:{}", JSONUtil.toJsonStr(user));
        user.setAge(27);
        return user;
    }

    @Override
    @CacheEvict(value = "cache:user", key = "#userId")
    public void remove(Long userId) {
        log.info("Service删除用户ID:{}", userId);
        // 此处省略走DB删除
    }

    @Override
    @CacheEvict(value = "cache:user", allEntries = true)
    public void removeAll() {
        log.info("Service删除所有用户缓存");
    }

    @Override
    @Cacheable(value = "cache:user", key = "#user.username")
    public User findOne(User user) {
        log.info("Service查询指定用户{}缓存!", user.getUsername());
        return new User(user.getUserID(), user.getUsername(), user.getPassword(), user.getAge()+1);
    }

    @Override
    @Cacheable(value = "cache:user", key = "#userId")
    public User findOne(Long userId) {
        log.info("Service查询指定用户{}缓存!", userId);
        return queryDB(userId);
    }

    @Override
    @CachePut(value = "cache:user", key = "#user.userID")
    public boolean update(User user) {
        log.info("Service更新指定用户{}缓存!", user.getUserID());
        user.setAge(user.getAge()+1);
        return true;
    }

    public User queryDB(Long userId) {
        log.info("Service查询指定用户{}DB数据!", userId);
        return new User(userId, "test", "test", 0);
    }
}
