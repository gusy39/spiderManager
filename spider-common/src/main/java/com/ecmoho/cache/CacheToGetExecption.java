package com.ecmoho.cache;

/**
 * Created by Administrator on 2015/10/28.
 */
public class CacheToGetExecption extends RuntimeException {
    public CacheToGetExecption() {
        super();
    }

    public CacheToGetExecption(String s) {
        super(s);
    }

    public CacheToGetExecption(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CacheToGetExecption(Throwable throwable) {
        super(throwable);
    }

    protected CacheToGetExecption(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
