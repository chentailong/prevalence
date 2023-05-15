package com.lon.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * 全局异常处理程序
 *
 * @author ctl
 * @date 2022/09/10
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 异常处理程序
     *
     * @return {@link ResultMsg}<{@link String}>
     */
    @ExceptionHandler(SQLException.class)
    public ResultMsg<String> exceptionHandler(SQLException exception) {
        String message = exception.getMessage();
        String ExceptionMsg = "Duplicate entry";
        if (message.contains(ExceptionMsg)) {
            String[] split = message.split(" ");
            String mag = split[2] + "已存在";
            return ResultMsg.error(mag);
        }
        return ResultMsg.error("未知错误");
    }

    /**
     * 自定义异常处理程序
     *
     * @param exception 异常
     * @return {@link ResultMsg}<{@link String}>
     */
    @ExceptionHandler(CustomException.class)
    public ResultMsg<String> exceptionHandler(CustomException exception) {
        log.info(exception.getMessage());
        return ResultMsg.error(exception.getMessage());
    }
}
