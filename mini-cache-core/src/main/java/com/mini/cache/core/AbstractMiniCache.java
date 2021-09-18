package com.mini.cache.core;

import org.springframework.util.StringUtils;

/**
 * 基于Spring cache接口，做基础实现，并且适配
 *
 * @author Tony lau
 */
public abstract class AbstractMiniCache implements MiniCache,  CacheStats{

    static final int MAXIMUM_LOCK = 1 << 30;

    private String name;

    /** 缓存命中率统计 */
    protected final CacheStatInfo statistics = new CacheStatInfo();

    /** 内部锁的对象数组 */
    private final Object[] LOCK_ARRAY;

    private final Object LOCK = new Object();

    /** LOCK数组对象的HashSlot计算 */
    private int modNum;

    /** 回源线程数 */
    private final int backSourceSize;

    public AbstractMiniCache(String name, int backSourceSize) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalStateException("cache name must not be null");
        }
        this.name = name.trim();
        this.backSourceSize = lookSizeFor(backSourceSize == 0 ? 256 : backSourceSize);
        this.modNum = this.backSourceSize - 1;
        this.LOCK_ARRAY = new Object[this.backSourceSize];
        for (int i = 0; i < this.backSourceSize; i++) {
            LOCK_ARRAY[i] = new Object();
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public long getVisitTimes() {
        return statistics.getVisitTimes();
    }

    @Override
    public long getHitTimes() {
        return statistics.getHitTimes();
    }

    @Override
    public double getHitRate() {
        return statistics.getHitRate();
    }

    public static final int lookSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_LOCK) ? MAXIMUM_LOCK : n + 1;
    }

    public Object getLock(Object key) {
        int hash  = (key == null) ? 0 : (key.hashCode() ^ (key.hashCode() >>> 16));
        int index = hash & modNum;
        return LOCK_ARRAY[index];
    }

    public Object getLock() {
        return this.LOCK;
    }
}
