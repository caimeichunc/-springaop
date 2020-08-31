package com.example.aopDemo.aspect;


import com.example.aopDemo.aspect.annotation.McPermission;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * 权限拦截器
 *定义当拦截到连接点做相应的处理操作
 * @author Mc
 */
@Slf4j
public class McInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        McPermission annotation = method.getAnnotation(McPermission.class);

        String[] value = annotation.value();

        if (value.length ==0){
            return methodInvocation.proceed();
        }

        boolean isPermit = false;

        if (value[0].equals("kei")){
           isPermit = true;
        }

        if (!isPermit){
            throw new Exception("权限不够，请联系管理人员");
        }

        return methodInvocation.proceed();

    }
}
