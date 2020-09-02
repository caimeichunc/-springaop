package com.example.aopDemo.aspect.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class PermissionAspect {

    @Pointcut("@annotation(com.example.aopDemo.aspect.annotation.McPermission)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        McPermission annotation = method.getAnnotation(McPermission.class);
        String[] value = annotation.value();

        if (value.length == 0) {
            return joinPoint.proceed();
        }

        boolean isPermit = false;

        if (value[0].equals("kei")) {
            isPermit = true;
        }

        if (!isPermit) {
            throw new Exception("权限不够，请联系管理人员");
        }

        return joinPoint.proceed();

    }

}
