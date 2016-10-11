package com.ecmoho.cache;

/**
 * Created by Administrator on 2015/10/28.
 */
public class CacheToPutExecption extends RuntimeException {
    public CacheToPutExecption() {
        super();
    }

    public CacheToPutExecption(String s) {
        super(s);
    }

    public CacheToPutExecption(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CacheToPutExecption(Throwable throwable) {
        super(throwable);
    }

    protected CacheToPutExecption(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
