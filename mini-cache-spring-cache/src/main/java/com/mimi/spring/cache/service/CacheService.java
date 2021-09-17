package com.mimi.spring.cache.service;

import com.mimi.spring.cache.Entity.User;

public interface CacheService {

    User save(User user);

    void remove(Long userId);

    void removeAll();

    User findOne(User user);

    User findOne(Long userId);

    boolean update(User user);
}
