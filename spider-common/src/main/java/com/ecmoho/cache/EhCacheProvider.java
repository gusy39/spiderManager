package com.ecmoho.cache;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/28.
 */
public class EhCacheProvider implements CacheProvider ,InitializingBean {

    private Ehcache cache;

    public Ehcache getCache() {
        return cache;
    }

    public void setCache(Ehcache cache) {
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
        cache.put(new Element(key, value));
    }

    @Override
    public void putToIdle(Serializable key, Object value, long expire) throws CacheToPutExecption {
        if (value == null) {
            throw new CacheToPutExecption("the cache value cannot be empty");
        }
        Element element = new Element(key, value);
        element.setTimeToIdle(Integer.valueOf(expire+""));
        cache.put(new Element(key, value));

    }

    @Override
    public void putToLive(Serializable key, Object value, long expire) throws CacheToPutExecption {
        if (value == null) {
            throw new CacheToPutExecption("the cache value cannot be empty");
        }
        Element element = new Element(key, value);
        element.setTimeToLive(Integer.valueOf(expire+""));
        cache.put(new Element(key, value));

    }

    @Override
    public <T> T get(Serializable key) throws CacheToGetExecption {
        Element element = cache.get(key);
        if(element==null) return null;
        return (T)element.getObjectValue();

    }

    @Override
    public boolean isExists(Serializable key) throws CacheToGetExecption {
        return get(key) != null;
    }

    @Override
    public void remove(Serializable key) throws CacheToGetExecption {
        if (key != null) {
            cache.remove(key);
        }
    }

    @Override
    public void remove(Serializable... key) throws CacheToGetExecption {
        if (key != null) {
            for (Serializable k : key) {
                cache.remove(k);
            }
        }
    }
}
