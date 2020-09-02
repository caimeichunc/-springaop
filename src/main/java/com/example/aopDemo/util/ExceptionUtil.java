package com.example.aopDemo.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.lang.reflect.Method;


public class ExceptionUtil {

    private ExceptionUtil() {
    }

    /**
     * 说明：拼接异常信息方法
     *
     * @param e       异常
     * @param message 异常信息
     * @return 异常信息
     */
    public static StringBuilder appendExceptionInfo(Exception e, StringBuilder message) {
        try {
            Method method = e.getClass().getMethod("getBindingResult");
            BindingResult bindingResult = (BindingResult) method.invoke(e);

            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                message.append(fieldError.getDefaultMessage()).append(";");
            }
        } catch (Exception ignored) {
        }

        return message;
    }
}
