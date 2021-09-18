package com.mini.cache.core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.LongAdder;

public class CacheStatInfo {

    private LongAdder visitTimes = new LongAdder();;

    private LongAdder hitTimes = new LongAdder();;

    public double getHitRate() {
        BigDecimal visitTimesBigDecimal = new BigDecimal(visitTimes.longValue());
        BigDecimal hitTimesBigDecimal = new BigDecimal(hitTimes.longValue());
        return hitTimesBigDecimal.divide(visitTimesBigDecimal, 2, RoundingMode.HALF_UP).doubleValue();
    }

    public long getHitTimes() {
        return hitTimes.longValue();
    }

    public long getVisitTimes() {
        return visitTimes.longValue();
    }

    public void addHitTimes(long visit, long hit) {
        visitTimes.add(visit);
        hitTimes.add(hit);
    }

}
