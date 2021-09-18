package com.mini.cache.core;

/**
 * 缓存统计
 *
 * @author dingmeikun
 * @date 2021-05-18
 */
public interface CacheStats {

    /**
     * 统计总访问次数
     *
     * @return
     */
    long getVisitTimes();

    /**
     * 统计总命中次数
     *
     * @return
     */
    long getHitTimes();

    /**
     * 统计缓存命中率
     *
     * @return
     */
    double getHitRate();
}
