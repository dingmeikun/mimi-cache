package com.mimi.spring.cache;

import com.mimi.spring.cache.Entity.User;
import com.mimi.spring.cache.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CacheApplication.class)
public class CacheTest {

    @Resource(name = "springCache")
    private CacheService springCache;

    @Resource(name = "caffeineCache")
    private CacheService caffeineCache;

    @Resource(name = "compositeCache")
    private CacheService compositeCache;

    /**
     * 本地缓存 - SpringCache缓存
     */
    @Test
    public void testSpringCache() {
        log.info(">>>>>>>>>>>>>>>>>>>> 测试 SpringCache缓存");
        springCache.findOne(1L);
        springCache.findOne(1L);
        System.out.println();

        springCache.remove(1L);
        System.out.println();

        springCache.findOne(1L);
        springCache.findOne(1L);
        System.out.println();

        springCache.removeAll();
        System.out.println();

        springCache.findOne(1L);
        springCache.findOne(1L);
        System.out.println();
    }

    /**
     * 本地缓存 - Caffeine缓存
     */
    @Test
    public void testCaffeineCache() {
        log.info(">>>>>>>>>>>>>>>>>>>> 测试 Caffeine缓存");
        caffeineCache.findOne(1L);
        User one = caffeineCache.findOne(1L);

        User two = caffeineCache.findOne(one);
        one.setUserID(2L);
        User three = caffeineCache.findOne(one);

        caffeineCache.remove(1L);
        caffeineCache.findOne(1L);
    }

    /**
     * 组合缓存 - 用SpringCache的方式，操作Caffeine缓存（也是一层本地缓存）
     */
    @Test
    public void testCompositeCache() {
        log.info(">>>>>>>>>>>>>>>>>>>> 测试 CompositeCache缓存");
        compositeCache.findOne(1L);
        compositeCache.findOne(1L);
        System.out.println();

        compositeCache.remove(1L);
        System.out.println();

        compositeCache.findOne(1L);
        compositeCache.findOne(1L);
        System.out.println();

        compositeCache.removeAll();
        System.out.println();

        compositeCache.findOne(1L);
        compositeCache.findOne(1L);
        System.out.println();
    }
}
