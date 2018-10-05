package org.springstarttoend.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author mh_liu
 * @create 2018/10/5 下午4:22
 */
public interface Advice extends MethodInterceptor{

    Pointcut getPointcut();
}
