package com.ecmoho.cache;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/28.
 */
public interface CacheProvider {

    void put(Serializable key, Object value) throws CacheToPutExecption;

    /**
     * 设定允许对象处于空闲状态的最长时间，以毫秒为单位
     * @param key
     * @param value
     * @param expire
     * @throws CacheToPutExecption
     */
    void putToIdle(Serializable key, Object value,long expire) throws CacheToPutExecption;

    /**
     *设定对象允许存在于缓存中的最长时间，以秒为单位
     * @param key
     * @param value
     * @param expire
     * @throws CacheToPutExecption
     */
    void putToLive(Serializable key, Object value,long expire) throws CacheToPutExecption;

    <T> T get(Serializable key) throws  CacheToGetExecption;

    boolean isExists(Serializable key) throws  CacheToGetExecption;

    void remove(Serializable key) throws  CacheToGetExecption;

    void remove(Serializable... key) throws  CacheToGetExecption;
}
