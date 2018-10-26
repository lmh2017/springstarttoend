package org.springstarttoend.aop.freamwork;

import org.springstarttoend.aop.Advice;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author mh_liu
 * @create 2018/10/10 下午5:47
 * 在spring AOP源码中，这个类叫做Advised,获取被代理对象的一些准备信息
 */
public interface AopConfig {

    Class<?> getTargetClass();

    Object getTargetObject();

    boolean isProxyTargetClass();

    Class<?>[] getProxiedInterfaces();

    boolean isInterfaceProxied(Class<?> intf);

    List<Advice> getAdvices();

    void addAdvice(Advice advice);

    List<Advice> getAdvices(Method method/*,Class<?> targetClass*/);

    void setTargetObject(Object obj);

}
