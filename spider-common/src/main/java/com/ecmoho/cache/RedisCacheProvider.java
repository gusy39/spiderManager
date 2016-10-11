package com.ecmoho.cache;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/10/28.
 */
public class RedisCacheProvider implements CacheProvider ,InitializingBean {

    private RedisTemplate cache;

    public RedisTemplate getCache() {
        return cache;
    }

    public void setCache(RedisTemplate cache) {
        this.cache = cache;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(cache);
    }

    @Override
    public void put(Serializable key, Object value) throws CacheToPutExecption {
        if (value == null) {
            throw new CacheToPutExecption("the cache value cannot be empty");
        }
        cache.opsForValue().set(key,value);
    }

    public void put(Serializable key,Serializable hKey, Object hValue){
        cache.opsForHash().put(key,hKey,hValue);
    }


    @Override
    public void putToIdle(Serializable key, Object value, long expire) throws CacheToPutExecption {
        throw new CacheToPutExecption("redis 不支持该特性");
    }

    @Override
    public void putToLive(Serializable key, Object value, long expire) throws CacheToPutExecption {
        if (value == null) {
            throw new CacheToPutExecption("the cache value cannot be empty");
        }
        cache.opsForValue().set(key,value,expire, TimeUnit.MILLISECONDS);

    }

    public void putToLive(Serializable key,Serializable hKey, Object hValue,long expire){
        cache.opsForHash().put(key,hKey,hValue);
        cache.expire(key,expire,TimeUnit.MILLISECONDS);
    }

    @Override
    public <T> T get(Serializable key) throws CacheToGetExecption {
        Object o = cache.opsForValue().get(key);
        if (o != null) {
            return (T) o;
        }
        return null;
    }
    public <T> T get(Serializable key,Serializable hKey) throws CacheToGetExecption {
        Object o = cache.opsForHash().get(key, hKey);
        if (o != null) {
            return (T) o;
        }
        return null;
    }


    @Override
    public boolean isExists(Serializable key) throws CacheToGetExecption {
        return cache.hasKey(key);
    }

    @Override
    public void remove(Serializable key) throws CacheToGetExecption {
        if (key != null) {
            cache.delete(key);
        }
    }

    @Override
    public void remove(Serializable... key) throws CacheToGetExecption {
        if (key != null) {
            cache.delete(Arrays.asList(key));
        }
    }

    public void put(String key, Object value) throws CacheToPutExecption {
        if (value == null) {
            throw new CacheToPutExecption("the cache value cannot be empty");
        }
        cache.opsForValue().set(key,value);
    }

    public <T> T get(String key) throws CacheToGetExecption {
        Object o = cache.opsForValue().get(key);
        if (o != null) {
            return (T) o;
        }
        return null;
    }
}
