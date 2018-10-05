package org.springstarttoend.aop;

/**
 * @author mh_liu
 * @create 2018/10/4 下午3:58
 */
public interface Pointcut {

    MethodMatcher getMethodMatcher();
    String getExpression();

}
