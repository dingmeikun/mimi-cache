package com.mimi.spring.cache.config;

import com.mimi.spring.cache.Entity.User;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

@Slf4j
public class localValueLoader<T> implements Callable<T> {

    private Long userId;

    public localValueLoader(Long userId) {
        this.userId = userId;
    }

    @Override
    public T call() throws Exception {
        log.info("caffeine查询DB:{}", userId);
        // 模拟请求DB
        return (T) new User(userId, "test", "test", 0);
    }
}
