package org.springstarttoend.beans.factory.config;

/**
 * @author mh_liu
 * @create 2018/7/10 下午5:38
 */
public interface SingletonBeanRegistry {

    void registerSingleton(String beanName,Object singletonObject);

    Object getSingleton(String beanName);
}
