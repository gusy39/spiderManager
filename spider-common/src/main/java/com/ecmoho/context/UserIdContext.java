package com.ecmoho.context;

/**
 * 存放用户Id的线程变量
 */
public class UserIdContext {

    private static final InheritableThreadLocal<Integer> holder = new InheritableThreadLocal<Integer>();

    public static void setUserId(Integer userId) {
        holder.set(userId);
    }

    public static void remove() {
        holder.remove();
    }

    public static Integer getUserId() {
        return holder.get();
    }

}
