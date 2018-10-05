package org.springstarttoend.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author mh_liu
 * @create 2018/10/5 下午4:36
 */
public class AspectJBeforeAdvice extends AbstractAspectJAdvice {

    public AspectJBeforeAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, Object adviceObject){
        super(adviceMethod,pointcut,adviceObject);
    }
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        this.invokeAdviceMethod();
        Object o = methodInvocation.proceed();
        return o;

    }
}
