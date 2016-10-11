package com.ecmoho.cache;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2015/10/28.
 */
public class MemoryCacheProvider implements CacheProvider {

    /**
     * 存储数据的map,至于为什么用ConcurrentHashMap，可以百度
     */
    private static Map<Object, CacheObject> pool = new ConcurrentHashMap<Object, CacheObject>();

    private class CacheObject {
        private Object value;
        private long timeout;
        private long activeTime;
        private long liveTime;

        public CacheObject() {
        }

        public CacheObject(Object value, long timeout) {
            this.value = value;
            this.timeout = timeout;
        }

        public long getActiveTime() {
            return activeTime;
        }

        public void setActiveTime(long activeTime) {
            this.activeTime = activeTime;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public long getTimeout() {
            return timeout;
        }

        public void setTimeout(long timeout) {
            this.timeout = timeout;
        }

        public long getLiveTime() {
            return liveTime;
        }

        public void setLiveTime(long liveTime) {
            this.liveTime = liveTime;
        }
    }

    /**
     * 写个static方法块，里创建一个定时器 15分钟回收一次
     */
    static {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                for (Iterator<Object> it = pool.keySet().iterator(); it.hasNext(); ) {
                    Object key = it.next();
                    CacheObject value = pool.get(key);
                    if (null != value && value.getTimeout() > 0L) {
                        if (value.getActiveTime() > 0L && (value.getActiveTime() + value.getTimeout() < System.currentTimeMillis())) {
                            pool.remove(key);
                            value = null;
                        }
                        if (value.getLiveTime() > 0L && (value.getLiveTime() + value.getTimeout() < System.currentTimeMillis())) {
                            pool.remove(key);
                            value = null;
                        }
                    }
                }
            }
        }, new Date(), 15 * 60 * 1000L);
    }

    @Override
    public void put(Serializable key, Object value) throws CacheToPutExecption {
        if (value == null) {
            throw new CacheToPutExecption("the cache value cannot be empty");
        }
        CacheObject co = new CacheObject(value, 0L);
        pool.put(key, co);
    }

    @Override
    public void putToIdle(Serializable key, Object value, long expire) throws CacheToPutExecption {
        if (value == null) {
            throw new CacheToPutExecption("the cache value cannot be empty");
        }
        CacheObject co = new CacheObject(value, expire);
        co.setActiveTime(System.currentTimeMillis());
        co.setLiveTime(0L);
        pool.put(key, co);
    }

    @Override
    public void putToLive(Serializable key, Object value, long expire) throws CacheToPutExecption {
        if (value == null) {
            throw new CacheToPutExecption("the cache value cannot be empty");
        }
        CacheObject co = new CacheObject(value, expire);
        co.setActiveTime(0L);
        co.setLiveTime(System.currentTimeMillis());
        pool.put(key, co);
    }

    @Override
    public <T> T get(Serializable key) throws CacheToGetExecption {
        CacheObject value = pool.get(key);
        // value不等于空情况下，判断保存时间＋超时时间 是否小于当前时间，小于说明过期了。从map中移除掉
        if ((null != value) && value.getTimeout() > 0) {
            if (value.getActiveTime() > 0L && (value.getActiveTime() + value.getTimeout() < System.currentTimeMillis())) {
                pool.remove(key);
                value = null;
            } else if (value.getActiveTime() > 0L && (value.getActiveTime() + value.getTimeout() > System.currentTimeMillis())) {
                value.setActiveTime(System.currentTimeMillis());
                value.setLiveTime(0L);
            }

            if (value.getLiveTime() > 0L && (value.getLiveTime() + value.getTimeout() < System.currentTimeMillis())) {
                pool.remove(key);
                value = null;
            }

        }
        if (null == value) {
            return null;
        }
        return (T) value.getValue();
    }

    @Override
    public boolean isExists(Serializable key) throws CacheToGetExecption {
        return get(key) != null;
    }

    @Override
    public void remove(Serializable key) throws CacheToGetExecption {
        if (key != null) {
            pool.remove(key);
        }
    }

    @Override
    public void remove(Serializable... key) throws CacheToGetExecption {
        if (key != null) {
            for (Serializable k : key) {
                pool.remove(k);
            }
        }
    }
}
