package org.springstarttoend.aop;

import java.lang.reflect.Method;

/**
 * @author mh_liu
 * @create 2018/10/4 下午4:01
 */
public interface MethodMatcher {

    boolean matches(Method method/*, Class<?> targetClass*/);
}
