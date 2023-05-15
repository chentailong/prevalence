package com.lon.common;

/**
 * 基于ThreadLocal封装工具类，用户保存或获取登录ID
 *
 * @author ctl
 * @date 2022/09/11
 */
public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }

}
