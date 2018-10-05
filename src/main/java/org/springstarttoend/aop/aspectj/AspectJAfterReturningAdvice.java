package org.springstarttoend.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author mh_liu
 * @create 2018/10/5 下午4:47
 */
public class AspectJAfterReturningAdvice extends AbstractAspectJAdvice {

    public AspectJAfterReturningAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, Object adviceObject){
        super(adviceMethod,pointcut,adviceObject);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object o = methodInvocation.proceed();
        //例如：调用TransactionManager的commit方法
        this.invokeAdviceMethod();
        return o;
    }
}
