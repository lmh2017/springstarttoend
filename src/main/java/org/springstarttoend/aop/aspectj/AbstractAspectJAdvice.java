package org.springstarttoend.aop.aspectj;

import org.springstarttoend.aop.Advice;
import org.springstarttoend.aop.Pointcut;

import java.lang.reflect.Method;

/**
 * @author mh_liu
 * @create 2018/10/5 下午4:30
 */
public abstract class AbstractAspectJAdvice implements Advice {

    protected Method adviceMethod;

    protected AspectJExpressionPointcut pointcut;

    protected Object adviceObject;

    public AbstractAspectJAdvice(Method adviceMethod,
                                 AspectJExpressionPointcut pointcut,
                                 Object adviceObject){

        this.adviceMethod = adviceMethod;
        this.pointcut = pointcut;
        this.adviceObject = adviceObject;
    }

    public void invokeAdviceMethod() throws  Throwable{

        adviceMethod.invoke(adviceObject);
    }


    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    public Method getAdviceMethod() {
        return adviceMethod;
    }

}
