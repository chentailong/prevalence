package com.lon.common;

import io.swagger.annotations.Api;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 结果消息
 *
 * @author ctl
 * @date 2022/09/09
 */
@Data
@Api(tags = "返回结果")
public class ResultMsg<T> implements Serializable {

    /**
     * 编码：200成功，0和其它数字为失败
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    /**
     * 动态数据
     */
    private Map map = new HashMap();

    public static <T> ResultMsg<T> success(T object) {
        ResultMsg<T> result = new ResultMsg<T>();
        result.data = object;
        result.code = 200;
        return result;
    }

    public static <T> ResultMsg<T> error(String msg) {
        ResultMsg result = new ResultMsg();
        result.msg = msg;
        result.code = 400;
        return result;
    }

    public ResultMsg<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
