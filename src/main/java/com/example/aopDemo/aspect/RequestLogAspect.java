package com.example.aopDemo.aspect;

import com.example.aopDemo.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@Component
@Aspect
public class RequestLogAspect {

    /**
     * 打印日志的响应数据的最大长度，否则提示成功
     */
    private static final int RESPONSE_MAX_LENGTH = 200;

    @Pointcut("(@annotation(org.springframework.web.bind.annotation.RequestMapping)"
            + "|| @annotation(org.springframework.web.bind.annotation.GetMapping)"
            + "|| @annotation(org.springframework.web.bind.annotation.PostMapping))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        // 记录请求日志信息
        StringBuilder logBuilder = new StringBuilder();

        // 获取请求url
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        logBuilder.append(request.getRequestURI()).append(" -> ");

        // 获取请求参数
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            logBuilder.append("请求参数 = ").append(args[0]).append("。");
        } else {
            logBuilder.append("无请求参数。");
        }

        try {
            // 执行请求方法
            Object result = joinPoint.proceed();

            // 拼接响应数据信息，若长度大于RESPONSE_MAX_LENGTH，则提示“请求成功”
            if (result.toString().length() > RESPONSE_MAX_LENGTH) {
                logBuilder.append("请求成功！响应数据过长，不显示。");
            } else {
                logBuilder.append("请求成功！响应数据 = ").append(result).append("。");
            }

            return result;
        } catch (BindException e) {
            logBuilder.append("请求失败！").append("参数错误 -> ");
            // 拼接参数错误信息日志
            ExceptionUtil.appendExceptionInfo(e, logBuilder);

            throw new BindException(e);
        } catch (Exception e) {
            logBuilder.append("请求失败！").append("处理异常 -> ").append(e).append("。");

            throw new Exception(e);
        } finally {
            // 计算请求执行时间
            long endTime = System.currentTimeMillis();
            logBuilder.append("请求执行时间 = ").append(endTime - startTime).append("ms。");

            // 输出请求日志
            log.info(logBuilder.toString());
        }
    }
}
