package com.lon.common;

/**
 * 自定义异常
 *
 * @author ctl
 * @date 2022/09/11
 */
public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }

}
