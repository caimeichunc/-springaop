package com.example.aopDemo.aspect.annotation;

import com.example.aopDemo.aspect.McInterceptor;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;


/**
 * @author Mc
 * Advisor ： Advice 和 Pointcut 组成的独立的单元，并且能够传给 proxy factory 对象。
 */
public class McAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {


    private final Advice advice;
    private final Pointcut pointcut;


    //创建一个指定切入点和通知的通知器
    public McAdvisor(McInterceptor interceptor) {
        this.advice = interceptor;
        this.pointcut = buildPointcut();
        this.setOrder(100);

    }

    private Pointcut buildPointcut() {
        //类级别的匹配
        Pointcut classPointCut = new AnnotationMatchingPointcut(McPermission.class, true);
        //方法级别的匹配
        Pointcut methodPointCut = AnnotationMatchingPointcut.forMethodAnnotation(McPermission.class);
        return new ComposablePointcut(classPointCut).union(methodPointCut);
    }


    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
        }
    }


}
